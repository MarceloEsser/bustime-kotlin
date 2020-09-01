package esser.marcelo.busoclock.database

import androidx.room.Database
import androidx.room.RoomDatabase
import esser.marcelo.busoclock.dao.BusTimeDao
import esser.marcelo.busoclock.model.favorite.FavoriteItineraries
import esser.marcelo.busoclock.model.favorite.FavoriteLine
import esser.marcelo.busoclock.model.schedules.Saturday
import esser.marcelo.busoclock.model.schedules.Sunday
import esser.marcelo.busoclock.model.schedules.Workingday

@Database(
    entities = arrayOf(
        FavoriteLine::class,
        Workingday::class,
        Saturday::class,
        Sunday::class,
        FavoriteItineraries::class
    ), version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    internal abstract fun busTimeDao(): BusTimeDao
}