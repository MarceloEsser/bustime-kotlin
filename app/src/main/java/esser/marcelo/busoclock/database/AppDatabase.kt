
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kotlin_unit_tests_examples.dao.BusTimeDao
import esser.marcelo.busoclock.model.LineWithSchedules

@Database(entities = arrayOf(LineWithSchedules::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    internal abstract fun viaCepDao(): BusTimeDao
}