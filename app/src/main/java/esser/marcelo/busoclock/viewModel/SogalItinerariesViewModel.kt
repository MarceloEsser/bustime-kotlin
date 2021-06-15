package esser.marcelo.busoclock.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import esser.marcelo.busoclock.repository.LineHolder
import esser.marcelo.busoclock.model.sogal.ItinerariesDTO
import esser.marcelo.busoclock.repository.service.sogalServices.SogalServiceDelegate
import esser.marcelo.busoclock.repository.service.wrapper.resource.Status
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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

    private val _itineraries = MutableLiveData<List<ItinerariesDTO>>()
    val itineraries: LiveData<List<ItinerariesDTO>> by lazy {
        viewModelScope.launch(dispatcher) {
            loadItineraries(null)
        }
        return@lazy _itineraries
    }

    suspend fun loadItineraries(onItinerariesLoaded: (() -> Unit)?) {
        service.getSogalItineraries(LineHolder.lineCode).collect { resource ->
            if (resource.requestStatus == Status.success) {
                _itineraries.postValue(resource.data?.itineraries)
                if(onItinerariesLoaded != null) {
                    onItinerariesLoaded()
                }
            }
        }
    }
}