package esser.marcelo.busoclock.repository.dao

import android.content.Context
import androidx.room.Room
import esser.marcelo.busoclock.interfaces.SaveLineDelegate
import esser.marcelo.busoclock.model.favorite.FavoriteLine
import esser.marcelo.busoclock.model.favorite.LineWithSchedules
import esser.marcelo.busoclock.model.schedules.Saturday
import esser.marcelo.busoclock.model.schedules.Sunday
import esser.marcelo.busoclock.model.schedules.Workingday
import esser.marcelo.busoclock.repository.dao.database.AppDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/08/20
 */


class DaoHelper(
    private val context: Context,
) : SaveLineDelegate {

    private val database: AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "bustime"
    ).build()

    private val bustimeDao by lazy {
        database.busTimeDao()
    }

    lateinit var workingdays: List<Workingday>
    lateinit var saturdays: List<Saturday>
    lateinit var sundays: List<Sunday>

    private var _lineId: Long? = null

    suspend fun insertLine(line: FavoriteLine): Long? {
        this._lineId = bustimeDao.insertLine(line)
        insertSchedules()
        return this._lineId
    }


    fun getAll(): Flow<List<LineWithSchedules>?> {
        return bustimeDao.getAll()
    }

    suspend fun getLines(lineId: Long): Flow<List<LineWithSchedules>?> {
        return bustimeDao.getLineBy(lineId)
    }

    suspend fun getLines(
        lineName: String,
        lineCode: String,
        lineWayDescription: String
    ): Flow<List<LineWithSchedules>?> {
        return bustimeDao.getLineBy(lineName, lineCode, lineWayDescription)
    }

    fun clearDatabase() {
        bustimeDao.deleteAllLines()
        bustimeDao.deleteAllSaturdays()
        bustimeDao.deleteAllWorkingdays()
        bustimeDao.deleteAllSundays()
    }

    suspend fun deleteLine(
        lineName: String,
        lineCode: String,
        lineWayDescription: String
    ) {
        findAndDeleteLine(lineName, lineCode, lineWayDescription)
    }

    private suspend fun findAndDeleteLine(
        lineName: String,
        lineCode: String,
        lineWayDescription: String
    ) {
        bustimeDao.getLineBy(name = lineName, code = lineCode, way = lineWayDescription).collect {
            if (!it.isNullOrEmpty()) {
                val lineId = it[0].line?.id ?: -1
                removeLineFromDatabaseBy(lineId)
            }
        }

    }

    private fun removeLineFromDatabaseBy(lineId: Long) {
        bustimeDao.deleteLineFrom(lineId)
        bustimeDao.deleteSaturdaysFrom(lineId)
        bustimeDao.deleteWorkingdaysFrom(lineId)
        bustimeDao.deleteSundaysFrom(lineId)
    }

    private fun insertSchedules() {
        insertWorkingday()
        insertSaturday()
        insertSunday()
    }

    private fun insertWorkingday() {
        val workingdays = mutableListOf<Workingday>()
        fillWorkingdays(workingdays)

        bustimeDao.insertWorkingdays(workingdays)
    }

    private fun fillWorkingdays(
        workingdays: MutableList<Workingday>
    ) {
        if (!this.workingdays.isNullOrEmpty()) {
            for (schedule in this.workingdays) {
                val workingday = schedule
                workingday.workindayKey = _lineId
                workingdays.add(workingday)
            }
        }
    }

    private fun insertSaturday() {
        val saturdays = mutableListOf<Saturday>()
        fillSaturdays(saturdays)

        bustimeDao.insertSaturdays(saturdays)
    }

    private fun fillSaturdays(
        saturdays: MutableList<Saturday>
    ) {
        if (!this.saturdays.isNullOrEmpty()) {
            for (schedule in this.saturdays) {
                val saturday = schedule
                saturday.saturdayKey = _lineId
                saturdays.add(saturday)

            }
        }
    }

    private fun insertSunday() {
        val sundays = mutableListOf<Sunday>()
        fillSundays(sundays)

        bustimeDao.insertSundays(sundays)
    }

    private fun fillSundays(
        sundays: MutableList<Sunday>
    ) {
        if (!this.sundays.isNullOrEmpty()) {
            for (schedule in this.sundays) {
                val sunday = schedule
                sunday.sundayKey = _lineId
                sundays.add(sunday)

            }
        }
    }

    override fun canInsertSchedules(isSogal: Boolean) {
        insertSchedules()
    }
}