package esser.marcelo.busoclock.model.sogal

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
class SchedulesDTO(

    @PrimaryKey(autoGenerate = true)
    var id: Long?,

    @SerializedName("hora")
    var hour: String? = null,

    @SerializedName("abrev")
    var abrev: String? = null,

    @SerializedName("apd")
    var apd: String? = null
) {
    fun isApd(): Boolean {
        return apd != null && apd.equals("S")
    }
}
