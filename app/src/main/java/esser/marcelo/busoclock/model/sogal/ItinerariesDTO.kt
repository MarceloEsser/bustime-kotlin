package esser.marcelo.busoclock.model.sogal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/08/20
 */

@Entity
class ItinerariesDTO(

    @PrimaryKey(autoGenerate = true)
    val itineraryId: Long,

    @SerializedName("cidade")
    @Expose
    @ColumnInfo(name = "city")
    var city: String? = null,

    @SerializedName("logradouro")
    @Expose
    @ColumnInfo(name = "street")
    var street: String? = null,

    @ColumnInfo(name = "lineId")
    var itineraryKey: Long? = null,

)
