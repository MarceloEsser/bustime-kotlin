package esser.marcelo.busoclock.model.boxModels

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

/**
 * @author Wottrich
 * @since 25/07/2019.
 */
@Entity
open class BoxSchedule {

    @Id var id: Long = 0

    var hour: String? = null
    var abrev: String? = null
    var apd: String = "N"


    lateinit var boxLineWorkdays: ToOne<BoxLine>

    lateinit var boxLineSaturdays: ToOne<BoxLine>

    lateinit var boxLineSundays: ToOne<BoxLine>


}
