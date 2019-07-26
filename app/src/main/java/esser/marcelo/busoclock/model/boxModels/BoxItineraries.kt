package esser.marcelo.busoclock.model.boxModels

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

/**
 * @author Wottrich
 * @since 25/07/2019.
 */

@Entity
open class BoxItineraries {

    @Id var id: Long = 0

    var city: String? = null
    var street: String? = null

    lateinit var boxLineItineraries: ToOne<BoxLine>
}