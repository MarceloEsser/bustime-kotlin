package esser.marcelo.busoclock.model

import androidx.room.Embedded
import androidx.room.Relation
import esser.marcelo.busoclock.model.schedules.Saturday
import esser.marcelo.busoclock.model.schedules.Sunday
import esser.marcelo.busoclock.model.schedules.Workingday
import org.jsoup.Connection

data class LineWithSchedules(

    @Embedded val line: BaseLine,

    @Relation(
        parentColumn = "lineId",
        entityColumn = "workingdayId"
    )
    val workingdays: List<Workingday>,

    @Relation(
        parentColumn = "lineId",
        entityColumn = "saturdayId"
    )
    val saturdays: List<Saturday>,

    @Relation(
        parentColumn = "lineId",
        entityColumn = "sundayId"
    )
    val sundays: List<Sunday>
)