package esser.marcelo.busoclock.dao

import esser.marcelo.busoclock.database.AppDatabase
import android.content.Context
import androidx.room.Room
import esser.marcelo.busoclock.model.favorite.LineWithSchedules

class DaoHelper(context: Context) {
    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "bustime"
    ).build()

    private var lineId: Long? = null
    private lateinit var line: LineWithSchedules

    fun getAll(): List<LineWithSchedules> {
        return db.busTimeDao().getAll()
    }

    fun insert(
        line: LineWithSchedules,
        onLineInserted: () -> Unit
    ) {
        this.line = line

        lineId = db.busTimeDao().insertLine(line.line!!)

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

        db.busTimeDao().insertWorkingdays(line.workingdays!!)
    }

    private fun insertSaturday() {
        for (schedule in line.saturdays!!) {
            schedule.saturdayKey = lineId
        }

        db.busTimeDao().insertSaturdays(line.saturdays!!)
    }

    private fun insertSunday() {
        for (schedule in line.sundays!!) {
            schedule.sundayKey = lineId
        }

        db.busTimeDao().insertSundays(line.sundays!!)
    }
}