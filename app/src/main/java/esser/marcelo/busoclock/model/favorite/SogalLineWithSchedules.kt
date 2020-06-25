package esser.marcelo.busoclock.model.favorite

import androidx.room.Embedded
import androidx.room.Relation
import esser.marcelo.busoclock.model.schedules.Saturday
import esser.marcelo.busoclock.model.schedules.Sunday
import esser.marcelo.busoclock.model.schedules.Workingday

data class SogalLineWithSchedules(

    @Embedded var line: FavoriteLine? = null,

    @Relation(
        parentColumn = "lineId",
        entityColumn = "workingdayKey"
    )
    var workingdays: List<Workingday>? = null,

    @Relation(
        parentColumn = "lineId",
        entityColumn = "saturdayKey"
    )
    var saturdays: List<Saturday>? = null,

    @Relation(
        parentColumn = "lineId",
        entityColumn = "sundayKey"
    )
    var sundays: List<Sunday>? = null
)