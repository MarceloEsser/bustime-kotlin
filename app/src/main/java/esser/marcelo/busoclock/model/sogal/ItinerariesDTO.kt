package esser.marcelo.busoclock.model.sogal

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/08/20
 */

class ItinerariesDTO(
    @SerializedName("cidade")
    @Expose
    var city: String? = null,

    @SerializedName("logradouro")
    @Expose
    var street: String? = null
)
