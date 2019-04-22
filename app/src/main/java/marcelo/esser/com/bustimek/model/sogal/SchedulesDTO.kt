package marcelo.esser.com.bustimek.model.sogal

import com.google.gson.annotations.SerializedName
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
class SchedulesDTO(
    @Id
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
