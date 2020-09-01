package esser.marcelo.busoclock.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import esser.marcelo.busoclock.dao.LineDAO
import esser.marcelo.busoclock.model.sogal.ItinerariesDTO
import esser.marcelo.busoclock.model.sogal.LinesDTO
import esser.marcelo.busoclock.service.sogalServices.SogalService
import esser.marcelo.busoclock.service.sogalServices.SogalServiceDelegate
import esser.marcelo.busoclock.service.wrapper.resource.Status
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/09/20
 */

class SogalItinerariesViewModel(
    private val service: SogalServiceDelegate,
    private val dispatcher: CoroutineContext
) : ViewModel() {

    val itineraries: LiveData<List<ItinerariesDTO>> by lazy {
        val _itineraries = MutableLiveData<List<ItinerariesDTO>>()
        viewModelScope.launch(dispatcher) {
            setItineraries(_itineraries)
        }
        return@lazy _itineraries
    }

    private suspend fun setItineraries(_itineraries: MutableLiveData<List<ItinerariesDTO>>) {
        service.getSogalItineraries(LineDAO.lineCode).collect { resource ->
            if (resource.requestStatus == Status.success)
                _itineraries.postValue(resource.data?.itineraries)
        }
    }
}