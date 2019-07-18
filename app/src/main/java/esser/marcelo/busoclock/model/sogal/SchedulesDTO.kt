package esser.marcelo.busoclock.model.sogal

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
class SchedulesDTO(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @SerializedName("hora")
    var hour: String = "",

    @SerializedName("abrev")
    var abrev: String =  "",

    @SerializedName("apd")
    var apd: String = "N"
) {
    fun isApd(): Boolean {
        return apd != null && apd.equals("S")
    }
}
