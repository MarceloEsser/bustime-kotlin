package esser.marcelo.busoclock.dao

import esser.marcelo.busoclock.database.AppDatabase
import android.content.Context
import androidx.room.Room
import esser.marcelo.busoclock.model.favorite.LineWithSchedules

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/08/20
 */

class DaoHelper(
    private val context: Context
) {

    private val database: AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "bustime"
    ).build()

    private var lineId: Long? = null
    private lateinit var line: LineWithSchedules

    private val bustimeDao by lazy {
        database.busTimeDao()
    }

    fun getAll(): List<LineWithSchedules> {
        return bustimeDao.getAll()
    }

    fun getLineBy(id: Long): List<LineWithSchedules> {
        return bustimeDao.getLineBy(id)
    }

    fun findLineBy(name: String, code: String, way: String): List<LineWithSchedules> {
        return bustimeDao.getLineBy(name, code, way)
    }

    fun deleteAll() {
        bustimeDao.clearDatabase()
    }

    fun insert(
        line: LineWithSchedules,
        onLineInserted: () -> Unit
    ) {
        this.line = line

        lineId = bustimeDao.insertLine(line.line!!)

        insertSchedules()

        onLineInserted()
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