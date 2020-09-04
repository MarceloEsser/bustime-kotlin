package esser.marcelo.busoclock.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import esser.marcelo.busoclock.repository.dao.LineDAO
import esser.marcelo.busoclock.model.favorite.LineWithSchedules
import esser.marcelo.busoclock.model.schedules.Saturday
import esser.marcelo.busoclock.model.schedules.Sunday
import esser.marcelo.busoclock.model.schedules.Workingday
import esser.marcelo.busoclock.repository.dao.DaoHelperDelegate
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

class FavoriteSchedulesViewModel(
    private val daoHelper: DaoHelperDelegate,
    private val dispatcher: CoroutineContext
) : ViewModel() {

    val workingDays: MutableLiveData<List<Workingday>> = MutableLiveData()
    val saturdays: MutableLiveData<List<Saturday>> = MutableLiveData()
    val sundays: MutableLiveData<List<Sunday>> = MutableLiveData()

    fun fillSchedules() = viewModelScope.launch(dispatcher) {
        daoHelper.getLines(LineDAO.lineId ?: 0).collect {
            if (it != null && it.isNotEmpty()) {
                val line = it[0]
                fillSchedules(line)
            }
        }
    }

    private fun fillSchedules(line: LineWithSchedules) {
        workingDays.postValue(line.workingdays)
        saturdays.postValue(line.saturdays)
        sundays.postValue(line.sundays)
    }
}