package esser.marcelo.busoclock.repository.dao

import androidx.room.*
import esser.marcelo.busoclock.model.favorite.FavoriteLine
import esser.marcelo.busoclock.model.favorite.LineWithSchedules
import esser.marcelo.busoclock.model.schedules.Saturday
import esser.marcelo.busoclock.model.schedules.Sunday
import esser.marcelo.busoclock.model.schedules.Workingday
import esser.marcelo.busoclock.model.sogal.ItinerariesDTO
import kotlinx.coroutines.flow.Flow

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
    fun getAll(): Flow<List<LineWithSchedules>?>

    @Transaction
    @Query("SELECT * FROM FavoriteLine WHERE id in (:id)")
    fun getLineBy(id: Long): Flow<List<LineWithSchedules>>

    @Transaction
    @Query("SELECT * FROM FavoriteLine WHERE line_name in (:name) and line_code in (:code) and line_way in (:way)")
    fun getLineBy(name: String, code: String, way: String): Flow<List<LineWithSchedules>>

    @Query("DELETE FROM Workingday")
    fun deleteAllWorkingdays()

    @Query("DELETE FROM Saturday")
    fun deleteAllSaturdays()

    @Query("DELETE FROM Sunday")
    fun deleteAllSundays()

    @Query("DELETE FROM FavoriteLine")
    fun deleteAllLines()

    @Query("DELETE FROM FavoriteLine WHERE id in (:lineId)")
    fun deleteLineFrom(lineId: Long)

    @Query("DELETE FROM Workingday WHERE lineId in (:lineId)")
    fun deleteWorkingdaysFrom(lineId: Long)

    @Query("DELETE FROM Saturday WHERE lineId in (:lineId)")
    fun deleteSaturdaysFrom(lineId: Long)

    @Query("DELETE FROM Sunday WHERE lineId in (:lineId)")
    fun deleteSundaysFrom(lineId: Long)

    @Insert
    suspend fun insertLine(lines: FavoriteLine): Long

    @Insert
    fun insertWorkingdays(schedules: List<Workingday>)

    @Insert
    fun insertSaturdays(schedules: List<Saturday>)

    @Insert
    fun insertSundays(schedules: List<Sunday>)

    @Insert
    fun insertItineraries(schedules: List<ItinerariesDTO>)

}