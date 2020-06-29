package esser.marcelo.busoclock.model.favorite

import androidx.room.Embedded
import androidx.room.Relation
import esser.marcelo.busoclock.model.schedules.Saturday
import esser.marcelo.busoclock.model.schedules.Sunday
import esser.marcelo.busoclock.model.schedules.Workingday

data class SogalLineWithSchedules(

    @Embedded var line: FavoriteLine? = null,

    @Relation(
        parentColumn = "id",
        entityColumn = "lineId",
        entity = Workingday::class
    )
    var workingdays: List<Workingday>? = null,

    @Relation(
        parentColumn = "id",
        entityColumn = "lineId",
        entity = Saturday::class
    )
    var saturdays: List<Saturday>? = null,

    @Relation(
        parentColumn = "id",
        entityColumn = "lineId",
        entity = Sunday::class
    )
    var sundays: List<Sunday>? = null
)