package esser.marcelo.busoclock.model.sogal

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import esser.marcelo.busoclock.model.BaseLine

class LinesDTO(
    @SerializedName("itinerarios")
    @Expose
    var itineraries: List<ItinerariesDTO>? = null
) : BaseLine()
