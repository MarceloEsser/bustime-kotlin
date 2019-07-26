package esser.marcelo.busoclock.model.boxModels

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

/**
 * @author Wottrich
 * @since 25/07/2019.
 */

@Entity
open class BoxLine {

    @Id var id: Long = 0

    var name: String? = null
    var code: String? = null
    var way: String? = null

    //@Backlink(to = "boxLine")
    //lateinit var workingDays: ToMany<BoxSchedule>
    //lateinit var saturdays: ToMany<BoxSchedule>
    //lateinit var sundays: ToMany<BoxSchedule>


    //LinesSogal
    //@Backlink(to = "boxLineItineraries")
    //lateinit var sogalItineraries: List<BoxItineraries>
}