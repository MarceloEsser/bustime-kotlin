package marcelo.esser.com.bustimek.model.sogal

import com.google.gson.annotations.SerializedName

class SchedulesDTO(
    @SerializedName("hora")
    private var hour: String? = null,

    @SerializedName("abrev")
    private var abrev: String? = null,

    @SerializedName("apd")
    private var apd: String? = null
)
