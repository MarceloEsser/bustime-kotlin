package esser.marcelo.busoclock.vvm.sogal.lines

import esser.marcelo.busoclock.adapter.GenericLinesAdapter2
import esser.marcelo.busoclock.box.Bus
import esser.marcelo.busoclock.dao.LineDAO
import esser.marcelo.busoclock.model.sogal.LinesDTO
import esser.marcelo.busoclock.service.sogalServices.SogalService
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
                    onSucces(buildList())
                } else {
                    onError("Houve algum erro em buscar as linhas")
                }
            }

        })
    }

    fun buildList () : List<GenericLinesAdapter2.Base> {
        val list = mutableListOf<GenericLinesAdapter2.Base>()

        if (Bus.getSogalLines().isNotEmpty()) {

            list.add(GenericLinesAdapter2.Section("Favoritos"))

            for (line in Bus.getSogalLines()) {

                list.add(GenericLinesAdapter2.Line(line.id, line.name ?: "", line.code ?: "", true))

            }

            list.add(GenericLinesAdapter2.Section("Outras linhas"))
        }

        for (line in linesList) {

            list.add(GenericLinesAdapter2.Line(-1, line.name, line.code, false))

        }

        return list
    }

    fun saveData(lineCode: String, lineName: String, lineWay: String) {
        LineDAO.lineName = lineName
        LineDAO.lineCode = lineCode
        LineDAO.lineWay = lineWay
    }
}