package esser.marcelo.busoclock.model.favorite

import androidx.room.Embedded
import androidx.room.Relation
import esser.marcelo.busoclock.model.schedules.Saturday
import esser.marcelo.busoclock.model.schedules.Sunday
import esser.marcelo.busoclock.model.schedules.Workingday

data class LineWithSchedules(

    @Embedded var line: FavoriteLine? = null,

    @Relation(
        parentColumn = "id",
        entityColumn = "lineId"
    )
    var workingdays: List<Workingday>? = null,

    @Relation(
        parentColumn = "id",
        entityColumn = "lineId"
    )
    var saturdays: List<Saturday>? = null,

    @Relation(
        parentColumn = "id",
        entityColumn = "lineId"
    )
    var sundays: List<Sunday>? = null
)