package marcelo.esser.com.bustimek.vvm.sogal.itineraries

import marcelo.esser.com.bustimek.dao.LinesDAO
import marcelo.esser.com.bustimek.model.sogal.ItinerariesDTO
import marcelo.esser.com.bustimek.model.sogal.LinesDTO
import marcelo.esser.com.bustimek.service.sogalServices.SogalService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SogalItinerariesActivityViewModel {
    private val service = SogalService().sogalSerivce()
    private val SEARCH_ITINERARIES: String = "buscaItinerarios"

    fun loadItineraries(
        onSucces: (itineraries: List<ItinerariesDTO>?) -> Unit,
        onError: (erroMessage: String) -> Unit
    ) {
        service.getSogalItineraries(action = SEARCH_ITINERARIES, linha = LinesDAO.lineCode)
            .enqueue(object : Callback<LinesDTO> {
                override fun onFailure(call: Call<LinesDTO>, t: Throwable) {
                    onError(t.message!!)
                }

                override fun onResponse(call: Call<LinesDTO>, response: Response<LinesDTO>) {
                    if (response.body() != null && response.body()!!.itineraries != null) {
                        onSucces(response.body()!!.itineraries!!)
                    }
                }

            })
    }
}