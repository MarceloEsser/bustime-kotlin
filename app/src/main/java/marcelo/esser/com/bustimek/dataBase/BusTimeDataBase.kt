package marcelo.esser.com.bustimek.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import marcelo.esser.com.bustimek.dataBase.converters.Converters
import marcelo.esser.com.bustimek.model.BaseLine
import marcelo.esser.com.bustimek.model.sogal.SchedulesDTO

@Database(entities = arrayOf(BaseLine::class, SchedulesDTO::class), version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class BusTimeDataBase : RoomDatabase() {
}