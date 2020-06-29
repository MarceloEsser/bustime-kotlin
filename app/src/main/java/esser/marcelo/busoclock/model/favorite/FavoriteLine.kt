package esser.marcelo.busoclock.model.favorite

import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import esser.marcelo.busoclock.model.schedules.BaseSchedule

@Entity
data class FavoriteLine(
    @PrimaryKey var id : Long? = null
    ) {
    @ColumnInfo(name = "line_name")
    var name: String = ""

    @ColumnInfo(name = "line_code")
    var code: String = ""

    @ColumnInfo(name = "line_way")
    var way: String = ""
}