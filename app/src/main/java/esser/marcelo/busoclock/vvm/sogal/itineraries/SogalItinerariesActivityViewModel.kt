package esser.marcelo.busoclock.vvm.sogal.itineraries

import esser.marcelo.busoclock.sla.LineDAO
import esser.marcelo.busoclock.model.sogal.ItinerariesDTO
import esser.marcelo.busoclock.model.sogal.LinesDTO
import esser.marcelo.busoclock.service.sogalServices.SogalService
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
        service.getSogalItineraries(action = SEARCH_ITINERARIES, linha = LineDAO.lineCode)
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