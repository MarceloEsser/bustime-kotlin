package marcelo.esser.com.bustimek.model.sogal

import com.google.gson.annotations.SerializedName

class SchedulesDTO(
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
