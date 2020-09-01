package esser.marcelo.busoclock.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Dao
import esser.marcelo.busoclock.dao.DaoHelper
import esser.marcelo.busoclock.dao.LineDAO
import esser.marcelo.busoclock.model.favorite.LineWithSchedules
import esser.marcelo.busoclock.model.schedules.BaseSchedule
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/09/20
 */

class FavoriteSchedulesViewModel(
    private val daoHelper: DaoHelper
) : ViewModel() {

    val workingDays: MutableLiveData<List<BaseSchedule>> = MutableLiveData()
    val saturdays: MutableLiveData<List<BaseSchedule>> = MutableLiveData()
    val sundays: MutableLiveData<List<BaseSchedule>> = MutableLiveData()

    fun fillSchedules() {
        GlobalScope.launch {

            val lines = daoHelper.getLineBy(LineDAO.lineId ?: 0)
            val line = lines[0]

            fillWorkingdays(line)
            fillSaturdays(line)
            fillSundays(line)
        }
    }

    private fun fillWorkingdays(line: LineWithSchedules) {
        val baseSchedules = mutableListOf<BaseSchedule>()
        for (workingday in line.workingdays!!) {
            baseSchedules.add(BaseSchedule(workingday.hour, workingday.abrev, workingday.apd))
        }

        workingDays.postValue(baseSchedules)
    }

    private fun fillSaturdays(line: LineWithSchedules) {
        val baseSchedules = mutableListOf<BaseSchedule>()
        for (saturday in line.saturdays!!) {
            baseSchedules.add(BaseSchedule(saturday.hour, saturday.abrev, saturday.apd))
        }

        saturdays.postValue(baseSchedules)
    }

    private fun fillSundays(line: LineWithSchedules) {
        val baseSchedules = mutableListOf<BaseSchedule>()
        for (sunday in line.sundays!!) {
            baseSchedules.add(BaseSchedule(sunday.hour, sunday.abrev, sunday.apd))
        }

        sundays.postValue(baseSchedules)
    }
}