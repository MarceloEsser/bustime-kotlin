package esser.marcelo.busoclock.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import esser.marcelo.busoclock.interfaces.SaveLineDelegate
import esser.marcelo.busoclock.repository.dao.LineDAO
import esser.marcelo.busoclock.model.schedules.Saturday
import esser.marcelo.busoclock.model.schedules.Sunday
import esser.marcelo.busoclock.model.schedules.Workingday
import esser.marcelo.busoclock.model.sogal.SogalResponse
import esser.marcelo.busoclock.repository.service.sogalServices.SogalServiceDelegate
import esser.marcelo.busoclock.repository.service.wrapper.resource.Resource
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

class SogalSchedulesViewModel(
    private val service: SogalServiceDelegate,
    private val dispatcher: CoroutineContext
) : ViewModel() {

    var saverDelegate: SaveLineDelegate? = null

    private val _workingDays = MutableLiveData<List<Workingday>>()
    val workingdays: LiveData<List<Workingday>> by lazy {
        loadSchedules()
        return@lazy _workingDays
    }

    private val _saturdays = MutableLiveData<List<Saturday>>()
    val saturdays: LiveData<List<Saturday>> by lazy {
        return@lazy _saturdays
    }

    private val _sundays = MutableLiveData<List<Sunday>>()
    val sundays: LiveData<List<Sunday>> by lazy {
        return@lazy _sundays
    }

    fun loadSchedules() {
        viewModelScope.launch(dispatcher) {
            service.getSchedules(LineDAO.lineWay?.way ?: "", LineDAO.lineCode).collect { resource ->
                if (resource.requestStatus == Status.success) {
                    fillSchedules(resource)

                    saveLineDelegate()
                }
            }
        }
    }

    private fun saveLineDelegate() {
        if (saverDelegate != null) {
            saverDelegate?.canInsertSchedules(isSogal = true)
        }
    }

    private fun fillSchedules(resource: Resource<SogalResponse?>) {
        _workingDays.postValue(resource.data?.workingDays)
        _saturdays.postValue(resource.data?.saturdays)
        _sundays.postValue(resource.data?.sundays)
    }
}
