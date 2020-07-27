package esser.marcelo.busoclock.dao

import esser.marcelo.busoclock.database.AppDatabase
import android.content.Context
import androidx.room.Room
import esser.marcelo.busoclock.model.favorite.SogalLineWithSchedules
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DaoHelper(context: Context) {
    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "bustime"
    ).build()

    fun getAll(): List<SogalLineWithSchedules> {
        return db.busTimeDao().getAll()
    }

    fun insert(
        line: SogalLineWithSchedules,
        onAllSaved: () -> Unit
    ) {
            val lineId = db.busTimeDao().insertLine(line.line!!)

            for (schedule in line.workingdays!!) {
                schedule.workindayKey = lineId
            }
            for (schedule in line.saturdays!!) {
                schedule.saturdayKey = lineId
            }
            for (schedule in line.sundays!!) {
                schedule.sundayKey = lineId
        }

        db.busTimeDao().insertWorkingdays(line.workingdays!!)
        db.busTimeDao().insertSaturdays(line.saturdays!!)
        db.busTimeDao().insertSundays(line.sundays!!)

        onAllSaved()
    }
}