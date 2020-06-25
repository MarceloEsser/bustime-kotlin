package esser.marcelo.busoclock.model.favorite

import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import esser.marcelo.busoclock.model.schedules.BaseSchedule

@Entity(tableName = "favorite_line")
data class FavoriteLine(
    @PrimaryKey var lineId : Long? = null
    ) {
    @ColumnInfo(name = "line_name")
    var name: String = ""

    @ColumnInfo(name = "line_code")
    var code: String = ""

    @ColumnInfo(name = "line_way")
    var way: String = ""
}