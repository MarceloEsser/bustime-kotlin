package esser.marcelo.busoclock.repository.dao

import android.content.Context
import androidx.room.Room
import esser.marcelo.busoclock.model.favorite.FavoriteLine
import esser.marcelo.busoclock.model.favorite.LineWithSchedules
import esser.marcelo.busoclock.repository.dao.database.AppDatabase
import esser.marcelo.busoclock.viewModel.SogalSchedulesViewModel
import esser.marcelo.busoclock.viewModel.VicasaSchedulesViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import okhttp3.internal.wait

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/08/20
 */

interface DaoHelperDelegate {
    fun getAll(): Flow<List<LineWithSchedules>?>
    suspend fun getLine(lineId: Long): LineWithSchedules?
    fun getLine(name: String, code: String, way: String): LineWithSchedules
    fun deleteAllLines()
    suspend fun insertLine(line: FavoriteLine)
}

class DaoHelper(
    context: Context,
    private val sogalSchedulesViewModel: SogalSchedulesViewModel,
    private val vicasaSchedulesViewModel: VicasaSchedulesViewModel
) : DaoHelperDelegate {

    private val database: AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "bustime"
    ).build()

    private val bustimeDao by lazy {
        database.busTimeDao()
    }

    override fun getAll(): Flow<List<LineWithSchedules>?> {
        return bustimeDao.getAll()
    }

    override suspend fun getLine(lineId: Long): LineWithSchedules? {
        var filteredLine: LineWithSchedules? = null
        bustimeDao.getLineBy(lineId).collect {
            if (!it.isNullOrEmpty()) {
                filteredLine = it[0]
            }
        }
        return filteredLine
    }

    override fun getLine(name: String, code: String, way: String): LineWithSchedules {
        return bustimeDao.getLineBy(name, code, way)[0]
    }

    override fun deleteAllLines() {
        bustimeDao.clearDatabase()
    }

    override suspend fun insertLine(line: FavoriteLine) {
        val lineId = bustimeDao.insertLine(line)

        sogalSchedulesViewModel.loadSchedules()

        insertSchedules(lineId)
    }

    private fun insertSchedules(lineId: Long) {
        insertWorkingday(lineId)
        insertSaturday(lineId)
        insertSunday(lineId)
    }

    private fun insertWorkingday(lineId: Long) {
        for (schedule in sogalSchedulesViewModel.workingDays.value!!) {
            schedule.workindayKey = lineId
        }

        bustimeDao.insertWorkingdays(sogalSchedulesViewModel.workingDays.value!!)
    }

    private fun insertSaturday(lineId: Long) {
        for (schedule in sogalSchedulesViewModel.saturdays.value!!) {
            schedule.saturdayKey = lineId
        }

        bustimeDao.insertSaturdays(sogalSchedulesViewModel.saturdays.value!!)
    }

    private fun insertSunday(lineId: Long) {
        for (schedule in sogalSchedulesViewModel.sundays.value!!) {
            schedule.sundayKey = lineId
        }

        bustimeDao.insertSundays(sogalSchedulesViewModel.sundays.value!!)
    }
}