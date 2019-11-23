package esser.marcelo.busoclock.vvm.sogal.lines

import esser.marcelo.busoclock.dao.LineDAO
import esser.marcelo.busoclock.model.LineWay
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
        onSucces: (linesDTO: List<LinesDTO>) -> Unit,
        onError: (errorMessage: String) -> Unit
    ) {
        service.getSogalList(action = SEARCH_LINES).enqueue(object : Callback<List<LinesDTO>> {
            override fun onFailure(call: Call<List<LinesDTO>>, t: Throwable) {
                onError(t.message!!)
            }

            override fun onResponse(call: Call<List<LinesDTO>>, response: Response<List<LinesDTO>>) {
                if (response.body() != null) {
                    onSucces(response.body()!!)
                    linesList = response.body()!! as MutableList<LinesDTO>
                } else {
                    onError("Houve algum erro em buscar as linhas")
                }
            }

        })
    }

    fun saveData(lineCode: String, lineName: String) {
        LineDAO.lineName = lineName
        LineDAO.lineCode = lineCode
    }
}