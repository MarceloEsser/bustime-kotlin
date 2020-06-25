import androidx.room.*
import esser.marcelo.busoclock.model.favorite.SogalLineWithSchedules

@Dao
interface BusTimeDao {

    @Transaction
    @Query("SELECT * FROM favorite_line")
    fun getAll(): List<SogalLineWithSchedules>

    @Insert
    fun insertAll(lines: SogalLineWithSchedules)

    @Delete
    fun delete(line: SogalLineWithSchedules)
}