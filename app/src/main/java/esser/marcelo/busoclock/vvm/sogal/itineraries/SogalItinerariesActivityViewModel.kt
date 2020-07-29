package esser.marcelo.busoclock.vvm.sogal.itineraries

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import esser.marcelo.busoclock.dao.LineDAO
import esser.marcelo.busoclock.model.sogal.ItinerariesDTO
import esser.marcelo.busoclock.model.sogal.LinesDTO
import esser.marcelo.busoclock.service.sogalServices.SogalService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SogalItinerariesActivityViewModel: ViewModel() {

    private val service = SogalService().sogalSerivce()
    private val SEARCH_ITINERARIES: String = "buscaItinerarios"
    var itineraries: MutableLiveData<List<ItinerariesDTO>> = MutableLiveData()


    /**
     * Function used to load the itineraries list
     * the HOFs referenced here is to help the SaveLineHelper
     * {@link import esser.marcelo.busoclock.helper.SaveLineHelper#loadItineraries({}, {}) loadItineraries}.
     */
    fun loadItineraries(
        onSucces: (itineraries: List<ItinerariesDTO>) -> Unit = {},
        onError: (erroMessage: String) -> Unit? = {}
    ) {
        service.getSogalItineraries(action = SEARCH_ITINERARIES, linha = LineDAO.lineCode)
            .enqueue(object : Callback<LinesDTO> {
                override fun onFailure(call: Call<LinesDTO>, t: Throwable) {
                    onError(t.message!!)
                }

                override fun onResponse(call: Call<LinesDTO>, response: Response<LinesDTO>) {
                    if (response.body() != null && response.body()!!.itineraries != null) {
                        itineraries.value = response.body()?.itineraries ?: listOf()

                        onSucces(response.body()?.itineraries ?: listOf())
                    }
                }

            })
    }
}