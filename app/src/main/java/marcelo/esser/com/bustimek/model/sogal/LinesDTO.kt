package marcelo.esser.com.bustimek.model.sogal

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LinesDTO(
    @SerializedName(value = "nome_master", alternate = ["nomeLinha"])
    @Expose
    var name: String? = null,

    @SerializedName(value = "linhas_master", alternate = ["linha"])
    @Expose
    var lineCode: String? = null,

    @SerializedName("itinerarios")
    @Expose
    var itineraries: List<ItinerariesDTO>? = null
)
