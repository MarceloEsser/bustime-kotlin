import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import esser.marcelo.busoclock.model.LineWithSchedules

@Dao
internal interface BusTimeDao {
    @Query("SELECT * FROM baseline")
    fun getAll(): List<LineWithSchedules>

    @Query("SELECT * FROM baseline WHERE lineId IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<LineWithSchedules>

    @Insert
    fun insertAll(vararg users: LineWithSchedules)

    @Delete
    fun delete(user: LineWithSchedules)
}