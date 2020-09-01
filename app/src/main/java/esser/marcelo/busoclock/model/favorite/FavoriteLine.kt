package esser.marcelo.busoclock.model.favorite

import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import esser.marcelo.busoclock.model.schedules.BaseSchedule

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/08/20
 */

@Entity
data class FavoriteLine(
    @PrimaryKey var id : Long? = null
    ) {
    @ColumnInfo(name = "is_sogal")
    var isSogal: Boolean = true

    @ColumnInfo(name = "line_name")
    var name: String = ""

    @ColumnInfo(name = "line_code")
    var code: String = ""

    @ColumnInfo(name = "line_way")
    var way: String = ""
}