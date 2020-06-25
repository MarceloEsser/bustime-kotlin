package esser.marcelo.busoclock.model.sogal

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ItinerariesDTO (
    @SerializedName("cidade")
    @Expose
    var city: String? = null,

    @SerializedName("logradouro")
    @Expose
    var street: String? = null
)
