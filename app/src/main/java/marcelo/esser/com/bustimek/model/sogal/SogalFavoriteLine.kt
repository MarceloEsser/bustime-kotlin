package marcelo.esser.com.bustimek.model.sogal

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
class SogalFavoriteLine(
    @Id
    var id: Long = 0,

    var workingDays: List<SchedulesDTO>?,
    var saturdays: List<SchedulesDTO>?,
    var sundays: List<SchedulesDTO>?,
    var lineName: String?,
    var lineCode: String?,
    var lineWay: String?


)