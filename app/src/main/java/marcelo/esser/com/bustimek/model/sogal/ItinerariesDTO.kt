package marcelo.esser.com.bustimek.model.sogal

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ItinerariesDTO(
    @SerializedName("cidade")
    @Expose
    private var city: String? = null,

    @SerializedName("logradouro")
    @Expose
    private var street: String? = null
)
