package esser.marcelo.busoclock.vvm.favorite

import esser.marcelo.busoclock.adapter.GenericLinesAdapter2
import esser.marcelo.busoclock.box.Bus
import esser.marcelo.busoclock.box.put
import esser.marcelo.busoclock.dao.LineDAO
import esser.marcelo.busoclock.helper.Constants
import esser.marcelo.busoclock.model.boxModels.BoxLine
import esser.marcelo.busoclock.model.boxModels.BoxSchedule
import esser.marcelo.busoclock.model.sogal.SchedulesDTO
import esser.marcelo.busoclock.model.sogal.SogalResponse
import esser.marcelo.busoclock.service.sogalServices.SogalService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteActivityViewModel {

    var lineWay: String = Constants.CB_WAY
    var lines: MutableList<GenericLinesAdapter2.Base> = mutableListOf()

    private var deletedLines: MutableList<GenericLinesAdapter2.Base> = mutableListOf()

    fun buildList () : List<GenericLinesAdapter2.Base> {
        val list = mutableListOf<GenericLinesAdapter2.Base>()

        if (Bus.getSogalLines().isNotEmpty()) {

            val boxLineList = Bus.getSogalLines()
            val countSogal = boxLineList.filter { it.isSogalLine }.size
            val countVicasa = boxLineList.filter { !it.isSogalLine }.size

            list.add(GenericLinesAdapter2.Section("Sogal"))

            for (line in boxLineList.filter { it.isSogalLine }) {

                val baseLine = GenericLinesAdapter2.Line(line.id, line.name ?: "", line.code ?: "", true)
                list.add(baseLine)

            }

            list.add(GenericLinesAdapter2.Section("Vicasa"))

            for (line in boxLineList.filter { !it.isSogalLine }) {

                val baseLine = GenericLinesAdapter2.Line(line.id, line.name ?: "", line.code ?: "", true)
                list.add(baseLine)

            }
        }

        return list
    }

    fun deleteFavorite (line: GenericLinesAdapter2.Line, success: () -> Unit) {

        if (line.boxId != null && line.boxId != -1L) {
            deletedLines.add(line)
            Bus.deleteLine(line.boxId!!, success)
        }

    }

    fun saveFavorite (lineWay: String, line: GenericLinesAdapter2.Line, success: () -> Unit) {

        var lineInTrash = false

        deletedLines.forEach { deletedLine ->
            if (deletedLine is GenericLinesAdapter2.Line) {
                if (deletedLine.code == line.code) {
                    lineInTrash = true
                    val boxLine = BoxLine(deletedLine.name, deletedLine.code, lineWay, true)
                    put(boxLine)
                    success()
                    return@forEach
                }
            }
        }

        if (lineInTrash)
            return

        GlobalScope.launch {

            loadSchedulesBy(lineWay, line.code, onSuccess = { workingDays, saturdays, sundays ->

                val boxLine = BoxLine(line.name, line.code, lineWay, true)

                for (workingDay in workingDays) {
                    val boxDay = BoxSchedule(workingDay.hour, workingDay.abrev, workingDay.apd)
                    boxLine.workingDays.add(boxDay)
                }

                for (saturday in saturdays) {
                    val boxDay = BoxSchedule(saturday.hour, saturday.abrev, saturday.apd)
                    boxLine.saturdays.add(boxDay)
                }

                for (sunday in sundays) {
                    val boxDay = BoxSchedule(sunday.hour, sunday.abrev, sunday.apd)
                    boxLine.sundays.add(boxDay)
                }

                put(boxLine)
                success()
            })

        }

    }

    private fun loadSchedulesBy(
            lineWay: String,
            lineCode: String,
            onSuccess: (workingDays: List<SchedulesDTO>, saturdays: List<SchedulesDTO>, sundays: List<SchedulesDTO>) -> Unit,
            onError: (errorMessage: String) -> Unit = {}
    ) {
        SogalService().sogalService().getSogalSchedulesBy(lineWay = lineWay, lineCode = lineCode)
                .enqueue(object : Callback<SogalResponse> {
                    override fun onResponse(call: Call<SogalResponse>, response: Response<SogalResponse>) {
                        response.body()?.let { sogalResponse ->
                            if (sogalResponse.workingDays != null && sogalResponse.saturdays != null && sogalResponse.sundays != null) {
                                onSuccess(sogalResponse.workingDays!!, sogalResponse.saturdays!!, sogalResponse.sundays!!)
                            }
                        }
                    }

                    override fun onFailure(call: Call<SogalResponse>, t: Throwable) {
                        onError(t.message.toString())
                    }
                })
    }

    fun saveData(lineCode: String, lineName: String, lineWay: String) {
        LineDAO.lineName = lineName
        LineDAO.lineCode = lineCode
        LineDAO.lineWay = lineWay
    }

}