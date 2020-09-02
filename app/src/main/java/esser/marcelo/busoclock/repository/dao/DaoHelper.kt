package esser.marcelo.busoclock.repository.dao

import android.content.Context
import androidx.room.Room
import esser.marcelo.busoclock.model.favorite.FavoriteLine
import esser.marcelo.busoclock.model.favorite.LineWithSchedules
import esser.marcelo.busoclock.repository.dao.database.AppDatabase
import esser.marcelo.busoclock.repository.service.wrapper.resource.NetworkBoundResource
import esser.marcelo.busoclock.repository.service.wrapper.resource.Resource
import esser.marcelo.busoclock.viewModel.SogalSchedulesViewModel
import esser.marcelo.busoclock.viewModel.VicasaSchedulesViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/08/20
 */

interface DaoHelperDelegate {
    fun getAll(): Flow<List<LineWithSchedules>?>
    fun getLine(lineId: Long): LineWithSchedules
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

    private var lineId: Long? = null
    private lateinit var line: LineWithSchedules

    private val bustimeDao by lazy {
        database.busTimeDao()
    }

    override fun getAll(): Flow<List<LineWithSchedules>?> {
        return bustimeDao.getAll()
    }

    override fun getLine(lineId: Long): LineWithSchedules {
        return bustimeDao.getLineBy(lineId)[0]
    }

    override fun getLine(name: String, code: String, way: String): LineWithSchedules {
        return bustimeDao.getLineBy(name, code, way)[0]
    }

    override fun deleteAllLines() {
        bustimeDao.clearDatabase()
    }

    override suspend fun insertLine(line: FavoriteLine) {
        bustimeDao.insertLine(line)
    }

    private fun insertSchedules() {
        insertWorkingday()
        insertSaturday()
        insertSunday()
    }

    private fun insertWorkingday() {
        for (schedule in line.workingdays!!) {
            schedule.workindayKey = lineId
        }

        bustimeDao.insertWorkingdays(line.workingdays!!)
    }

    private fun insertSaturday() {
        for (schedule in line.saturdays!!) {
            schedule.saturdayKey = lineId
        }

        bustimeDao.insertSaturdays(line.saturdays!!)
    }

    private fun insertSunday() {
        for (schedule in line.sundays!!) {
            schedule.sundayKey = lineId
        }

        bustimeDao.insertSundays(line.sundays!!)
    }
}