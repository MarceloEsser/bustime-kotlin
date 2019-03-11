package marcelo.esser.com.bustimek.model.sogal

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SogalResponse(
    @SerializedName("nomeLinha")
    private var lineName: String? = null,

    @SerializedName(value = "horariosBCUteis", alternate = arrayOf("horariosCBUteis"))
    private var workingDays: List<SchedulesDTO>? = null,

    @SerializedName(value = "horariosBCSabado", alternate = arrayOf("horariosCBSabado"))
    private var saturdays: List<SchedulesDTO>? = null,

    @SerializedName(value = "horariosBCDomingo", alternate = ["horariosCBDomingo"])
    private var sundays: List<SchedulesDTO>? = null,

    @SerializedName("hora")
    private var hour: String? = null,

    @SerializedName("abrev")
    private var abrev: String? = null,

    @SerializedName("apd")
    private var apd: String? = null,

    @SerializedName("nome_master")
    @Expose
    private var name: String? = null,

    @SerializedName("linhas_master")
    @Expose
    private var lineCode: String? = null
)
