package esser.marcelo.busoclock.vvm.sogal.lines

import esser.marcelo.busoclock.adapter.GenericLinesAdapter2
import esser.marcelo.busoclock.box.Bus
import esser.marcelo.busoclock.box.boxList
import esser.marcelo.busoclock.box.put
import esser.marcelo.busoclock.dao.LineDAO
import esser.marcelo.busoclock.model.boxModels.BoxLine
import esser.marcelo.busoclock.model.boxModels.BoxSchedule
import esser.marcelo.busoclock.model.sogal.LinesDTO
import esser.marcelo.busoclock.model.sogal.SchedulesDTO
import esser.marcelo.busoclock.model.sogal.SogalResponse
import esser.marcelo.busoclock.service.sogalServices.SogalService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author Marcelo Esser
 * @since 19/02/19
 */
class SogalLinesActivityViewModel {
    private val service = SogalService().sogalSerivce()

    private val SEARCH_LINES: String = "buscaLinhas"

    var linesList: MutableList<LinesDTO> = ArrayList()

    var lines: MutableList<GenericLinesAdapter2.Line> = mutableListOf()

    fun loadSogalLines(
        onSucces: (linesDTO: List<GenericLinesAdapter2.Base>) -> Unit,
        onError: (errorMessage: String) -> Unit
    ) {
        service.getSogalList(action = SEARCH_LINES).enqueue(object : Callback<List<LinesDTO>> {
            override fun onFailure(call: Call<List<LinesDTO>>, t: Throwable) {
                onError(t.message!!)
            }

            override fun onResponse(call: Call<List<LinesDTO>>, response: Response<List<LinesDTO>>) {
                if (response.body() != null) {
                    linesList = response.body()!! as MutableList<LinesDTO>
                    lines.clear()
                    onSucces(buildList(true))
                } else {
                    onError("Houve algum erro em buscar as linhas")
                }
            }

        })
    }

    fun loadSchedulesBy(
        lineWay: String,
        lineCode: String,
        onSuccess: (workingDays: List<SchedulesDTO>, saturdays: List<SchedulesDTO>, sundays: List<SchedulesDTO>) -> Unit,
        onError: (errorMessage: String) -> Unit = {}
    ) {
        service.getSogalSchedulesBy(lineWay = lineWay, lineCode = lineCode)
            .enqueue(object : Callback<SogalResponse> {
                override fun onFailure(call: Call<SogalResponse>, t: Throwable) {
                    onError(t.message.toString())
                }

                override fun onResponse(call: Call<SogalResponse>, response: Response<SogalResponse>) {
                    response.body()?.let { sogalResponse ->
                        if (sogalResponse.workingDays != null && sogalResponse.saturdays != null && sogalResponse.sundays != null) {
                            onSuccess(sogalResponse.workingDays!!, sogalResponse.saturdays!!, sogalResponse.sundays!!)
                        }
                    }
                }

            })
    }

    fun buildList (firstTime: Boolean = false) : List<GenericLinesAdapter2.Base> {
        val list = mutableListOf<GenericLinesAdapter2.Base>()

        if (Bus.getSogalLines().isNotEmpty()) {

            list.add(GenericLinesAdapter2.Section("Favoritos"))

            for (line in Bus.getSogalLines()) {

                val baseLine = GenericLinesAdapter2.Line(line.id, line.name ?: "", line.code ?: "", true)
                list.add(baseLine)

            }

            list.add(GenericLinesAdapter2.Section("Outras linhas"))
        }

        for (line in linesList) {

            val baseLine = GenericLinesAdapter2.Line(-1, line.name, line.code, false)
            list.add(baseLine)
            if (firstTime)
                lines.add(baseLine)

        }

        return list
    }

    fun buildFilter (filter: GenericLinesAdapter2.Line.() -> Boolean) : List<GenericLinesAdapter2.Line> {
        return lines.filter { filter.invoke(it) }
    }

    fun saveFavorite (lineWay: String, line: GenericLinesAdapter2.Line, success: () -> Unit) {

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

    fun deleteFavorite (line: GenericLinesAdapter2.Line, success: () -> Unit) {

        if (line.boxId != null && line.boxId != -1L) {
            Bus.deleteLine(line.boxId!!, success)
        }

    }

    fun saveData(lineCode: String, lineName: String, lineWay: String) {
        LineDAO.lineName = lineName
        LineDAO.lineCode = lineCode
        LineDAO.lineWay = lineWay
    }
}