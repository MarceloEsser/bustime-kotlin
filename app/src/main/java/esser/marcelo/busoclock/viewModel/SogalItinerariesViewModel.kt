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
        loadItineraries(null)
        return@lazy _itineraries
    }

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun loadItineraries(onItinerariesLoaded: (() -> Unit)?) {
        viewModelScope.launch(dispatcher) {
            service.getSogalItineraries(LineHolder.lineCode).collect { resource ->
                when (resource.requestStatus) {
                    Status.success -> {
                        _itineraries.postValue(resource.data?.itineraries)
                        if (onItinerariesLoaded != null) {
                            onItinerariesLoaded()
                        }
                    }
                    Status.error -> _error.postValue(resource.message)
                }
            }
        }
    }
}