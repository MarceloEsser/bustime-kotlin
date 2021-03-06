package esser.marcelo.busoclock.model.sogal

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import esser.marcelo.busoclock.model.schedules.BaseSchedule
import esser.marcelo.busoclock.model.schedules.Saturday
import esser.marcelo.busoclock.model.schedules.Sunday
import esser.marcelo.busoclock.model.schedules.Workingday

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/08/20
 */

class SogalResponse(
    @SerializedName("nomeLinha")
    var lineName: String? = null,

    @SerializedName(value = "horariosBCUteis", alternate = arrayOf("horariosCBUteis"))
    var workingDays: List<Workingday>? = null,

    @SerializedName(value = "horariosBCSabado", alternate = arrayOf("horariosCBSabado"))
    var saturdays: List<Saturday>? = null,

    @SerializedName(value = "horariosBCDomingo", alternate = ["horariosCBDomingo"])
    var sundays: List<Sunday>? = null,

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
