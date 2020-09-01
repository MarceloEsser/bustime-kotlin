package esser.marcelo.busoclock.repository.dao

import androidx.room.*
import esser.marcelo.busoclock.model.favorite.FavoriteItineraries
import esser.marcelo.busoclock.model.favorite.FavoriteLine
import esser.marcelo.busoclock.model.favorite.LineWithSchedules
import esser.marcelo.busoclock.model.schedules.Saturday
import esser.marcelo.busoclock.model.schedules.Sunday
import esser.marcelo.busoclock.model.schedules.Workingday

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/08/20
 */

@Dao
interface BusTimeDao {

    @Transaction
    @Query("SELECT * FROM FavoriteLine")
    fun getAll(): List<LineWithSchedules>

    @Transaction
    @Query("SELECT * FROM FavoriteLine WHERE id in (:id)")
    fun getLineBy(id: Long): List<LineWithSchedules>

    @Transaction
    @Query("SELECT * FROM FavoriteLine WHERE line_name in (:name) and line_code in (:code) and line_way in (:way)")
    fun getLineBy(name: String, code: String, way: String): List<LineWithSchedules>

    @Query("DELETE FROM FavoriteLine")
    fun deleteLines()

    @Query("DELETE FROM Workingday")
    fun deleteWorkingdays()

    @Query("DELETE FROM Saturday")
    fun deleteSaturdays()

    @Query("DELETE FROM Sunday")
    fun deleteSundays()

    @Query("DELETE FROM FavoriteLine")
    fun clearDatabase()

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