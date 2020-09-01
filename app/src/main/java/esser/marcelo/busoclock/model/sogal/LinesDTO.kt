package esser.marcelo.busoclock.model.sogal

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import esser.marcelo.busoclock.model.BaseLine

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/08/20
 */

class LinesDTO(
    @SerializedName("itinerarios")
    @Expose
    var itineraries: List<ItinerariesDTO>? = null
) : BaseLine()
