package esser.marcelo.busoclock.model.favorite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class FavoriteItineraries (

    @PrimaryKey
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
    val itineraryKey: Long
)
