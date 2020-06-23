package esser.marcelo.busoclock.model.schedules

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
open class BaseSchedule(
    @SerializedName("hora")
    @ColumnInfo(name = "hour")
    var hour: String = "",

    @SerializedName("abrev")
    @ColumnInfo(name = "abrev")
    var abrev: String =  "",

    @SerializedName("apd")
    @ColumnInfo(name = "apd")
    var apd: String = "N"
) {
    fun isApd(): Boolean {
        return apd == "S"
    }
}