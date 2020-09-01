package esser.marcelo.busoclock.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import esser.marcelo.busoclock.repository.dao.LineDAO
import esser.marcelo.busoclock.model.schedules.BaseSchedule
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

class SogalSchedulesViewModel(
    private val service: SogalServiceDelegate,
    private val dispatcher: CoroutineContext
) : ViewModel() {

    private val _workingDays = MutableLiveData<List<BaseSchedule>>()
    val workingDays: LiveData<List<BaseSchedule>> by lazy {
        viewModelScope.launch(dispatcher) {
            loadSchedules()
        }
        return@lazy _workingDays
    }

    private val _saturdays = MutableLiveData<List<BaseSchedule>>()
    val saturdays: LiveData<List<BaseSchedule>> by lazy {
        return@lazy _saturdays
    }

    private val _sundays = MutableLiveData<List<BaseSchedule>>()
    val sundays: LiveData<List<BaseSchedule>> by lazy {
        return@lazy _sundays
    }

    private suspend fun loadSchedules() {
        service.getSchedules(LineDAO.lineWay?.way ?: "", LineDAO.lineCode).collect { resource ->
            if (resource.requestStatus == Status.success) {
                _workingDays.postValue(resource.data?.workingDays)
                _saturdays.postValue(resource.data?.saturdays)
                _sundays.postValue(resource.data?.sundays)
            }
        }
    }
}
