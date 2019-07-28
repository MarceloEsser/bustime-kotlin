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
open class BoxLine() {

    @Id var id: Long = 0

    var name: String? = null
    var code: String? = null
    var way: String? = null
    var isSogalLine: Boolean = false

    constructor(name: String, code: String, way: String, isSogalLine: Boolean) : this() {
        this.name = name
        this.code = code
        this.way = way
        this.isSogalLine = isSogalLine
    }

    @Backlink(to = "boxLineWorkdays")
    lateinit var workingDays: ToMany<BoxSchedule>

    @Backlink(to = "boxLineSaturdays")
    lateinit var saturdays: ToMany<BoxSchedule>

    @Backlink(to = "boxLineSundays")
    lateinit var sundays: ToMany<BoxSchedule>


    //LinesSogal
    @Backlink(to = "boxLineItineraries")
    lateinit var sogalItineraries: ToMany<BoxItineraries>

}