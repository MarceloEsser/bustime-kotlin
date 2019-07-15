package marcelo.esser.com.bustimek.model.sogal

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import marcelo.esser.com.bustimek.model.LineCodeLineName

class LinesDTO(
    @SerializedName("itinerarios")
    @Expose
    var itineraries: List<ItinerariesDTO>? = null
) : LineCodeLineName()
