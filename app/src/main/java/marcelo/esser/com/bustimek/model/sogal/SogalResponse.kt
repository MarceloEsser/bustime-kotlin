package marcelo.esser.com.bustimek.model.sogal

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SogalResponse(
    @SerializedName("nomeLinha")
    var lineName: String? = null,

    @SerializedName(value = "horariosBCUteis", alternate = arrayOf("horariosCBUteis"))
    var workingDays: List<SchedulesDTO>? = null,

    @SerializedName(value = "horariosBCSabado", alternate = arrayOf("horariosCBSabado"))
    var saturdays: List<SchedulesDTO>? = null,

    @SerializedName(value = "horariosBCDomingo", alternate = ["horariosCBDomingo"])
    var sundays: List<SchedulesDTO>? = null,

    @SerializedName("hora")
    var hour: String? = null,

    @SerializedName("abrev")
    var abrev: String? = null,

    @SerializedName("apd")
    var apd: String? = null,

    @SerializedName("nome_master")
    @Expose
    var name: String? = null,

    @SerializedName("linhas_master")
    @Expose
    var lineCode: String? = null
)
