package marcelo.esser.com.bustimek.model.sogal

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LinesDTO(
    @SerializedName(value = "nome_master", alternate = ["nomeLinha"])
    @Expose
    private var name: String? = null,

    @SerializedName(value = "linhas_master", alternate = ["linha"])
    @Expose
    private var lineCode: String? = null,

    @SerializedName("itinerarios")
    @Expose
    private var itineraries: List<ItinerariesDTO>? = null
)
