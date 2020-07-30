package esser.marcelo.busoclock.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import esser.marcelo.busoclock.model.favorite.FavoriteItineraries
import esser.marcelo.busoclock.model.favorite.FavoriteLine
import esser.marcelo.busoclock.model.favorite.LineWithSchedules
import esser.marcelo.busoclock.model.schedules.Saturday
import esser.marcelo.busoclock.model.schedules.Sunday
import esser.marcelo.busoclock.model.schedules.Workingday

@Dao
interface BusTimeDao {

    @Transaction
    @Query("SELECT * FROM FavoriteLine")
    fun getAll(): List<LineWithSchedules>

    @Insert
    fun insertLine(lines: FavoriteLine): Long

    @Insert
    fun insertWorkingdays(schedules: List<Workingday>)

    @Insert
    fun insertSaturdays(schedules: List<Saturday>)

    @Insert
    fun insertSundays(schedules: List<Sunday>)

    @Insert
    fun insertItineraries(schedules: List<FavoriteItineraries>)

}