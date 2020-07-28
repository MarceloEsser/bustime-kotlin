package esser.marcelo.busoclock.model.schedules

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Saturday(

    @PrimaryKey(autoGenerate = true)
    var saturdayId: Long? = null,

    @ColumnInfo(name = "lineId")
    var saturdayKey: Long? = null,

    @SerializedName("hora")
    var hour: String = "",

    @ColumnInfo(name = "abrev")
    var abrev: String = "",

    @SerializedName("apd")
    var apd: String = "N"
) {
    fun isApd(): Boolean {
        return apd == "S"
    }
}