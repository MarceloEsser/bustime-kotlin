package marcelo.esser.com.bustimek.vvm.lines

import marcelo.esser.com.bustimek.model.sogal.LinesDTO
import marcelo.esser.com.bustimek.service.sogalServices.SogalService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author Marcelo Esser
 * @since 19/02/19
 */
class LinesActivityViewModel {
    private val service = SogalService().sogalSerivce()
    private val SEARCH_LINES: String = "buscaLinhass"

    fun loadSogalLines(
        onSucces: (linesDTO: List<LinesDTO>) -> Unit,
        onError: (errorMessage: String) -> Unit
    ) {
        service.getSogalList(SEARCH_LINES).enqueue(object : Callback<List<LinesDTO>> {
            override fun onFailure(call: Call<List<LinesDTO>>, t: Throwable) {
                onError(t.message!!)
            }

            override fun onResponse(call: Call<List<LinesDTO>>, response: Response<List<LinesDTO>>) {
                if(response.body() != null){
                    onSucces(response.body()!!)
                }else{
                    onError("Houve algum erro em buscar as linhas")
                }
            }

        })
    }
}