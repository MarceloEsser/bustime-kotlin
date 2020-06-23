import android.content.Context
import androidx.room.Room
import esser.marcelo.busoclock.model.LineWithSchedules

class DaoHelper (context: Context) {
    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "bustime"
    ).build()

    fun getAll(): List<LineWithSchedules> {
        return db.viaCepDao().getAll()
    }

    fun insert(address: LineWithSchedules) {
        db.viaCepDao().insertAll(address)
    }
}