package esser.marcelo.busoclock.viewModel.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import esser.marcelo.busoclock.repository.LineHolder
import esser.marcelo.busoclock.model.favorite.LineWithSchedules
import esser.marcelo.busoclock.model.schedules.Saturday
import esser.marcelo.busoclock.model.schedules.Sunday
import esser.marcelo.busoclock.model.schedules.Workingday
import esser.marcelo.busoclock.repository.dao.database.BusTimeDao
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
    private val dao: BusTimeDao,
    private val dispatcher: CoroutineContext
) : ViewModel() {

    val workingDays: MutableLiveData<List<Workingday>> = MutableLiveData()
    val saturdays: MutableLiveData<List<Saturday>> = MutableLiveData()
    val sundays: MutableLiveData<List<Sunday>> = MutableLiveData()
    var isSogal: Boolean? = null

    fun fillSchedules() = viewModelScope.launch(dispatcher) {
        dao.getLineBy(LineHolder.lineId ?: 0).collect {
            if (it.isNotEmpty()) {
                val line = it[0]
                isSogal = line.line?.isSogal
                fillSchedules(line)
            }
        }
    }

    private fun fillSchedules(line: LineWithSchedules) {
        workingDays.postValue(line.workingdays)
        saturdays.postValue(line.saturdays)
        sundays.postValue(line.sundays)
    }

    fun deleteLine() {
        viewModelScope.launch(dispatcher) {
            findAndDeleteLine(
                lineName = LineHolder.lineName,
                lineCode = LineHolder.lineCode,
                lineWayDescription = LineHolder.lineWay?.description ?: ""
            )
        }
    }

    private suspend fun findAndDeleteLine(
        lineName: String,
        lineCode: String,
        lineWayDescription: String
    ) {
        dao.getLineBy(name = lineName, code = lineCode, way = lineWayDescription).collect {
            if (!it.isNullOrEmpty()) {
                val lineId = it[0].line?.id ?: -1
                removeLineFromDatabaseBy(lineId)
            }
        }

    }

    private fun removeLineFromDatabaseBy(lineId: Long) {
        dao.deleteLineFrom(lineId)
        dao.deleteSaturdaysFrom(lineId)
        dao.deleteWorkingdaysFrom(lineId)
        dao.deleteSundaysFrom(lineId)
    }
}