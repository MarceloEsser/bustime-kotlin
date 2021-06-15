package esser.marcelo.busoclock.repository.dao.database

import androidx.room.Database
import androidx.room.RoomDatabase
import esser.marcelo.busoclock.model.favorite.FavoriteLine
import esser.marcelo.busoclock.model.schedules.Saturday
import esser.marcelo.busoclock.model.schedules.Sunday
import esser.marcelo.busoclock.model.schedules.Workingday
import esser.marcelo.busoclock.model.sogal.ItinerariesDTO

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/08/20
 */

@Database(
    entities = [FavoriteLine::class, Workingday::class, Saturday::class, Sunday::class, ItinerariesDTO::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    internal abstract fun busTimeDao(): BusTimeDao
}