import android.content.Context
import androidx.room.Room
import esser.marcelo.busoclock.model.favorite.SogalLineWithSchedules

class DaoHelper (context: Context) {
    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "bustime"
    ).build()

    fun getAll(): List<SogalLineWithSchedules> {
        return db.busTimeDao().getAll()
    }

    fun insert(address: SogalLineWithSchedules) {
        db.busTimeDao().insertAll(address)
    }
}