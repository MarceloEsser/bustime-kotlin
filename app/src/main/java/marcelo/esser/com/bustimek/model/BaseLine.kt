package marcelo.esser.com.bustimek.model

import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import marcelo.esser.com.bustimek.model.sogal.SchedulesDTO

@Entity
open class BaseLine(

    @SerializedName(value = "nome_master", alternate = ["nomeLinha"])
    @Expose
    var name: String = "",

    @SerializedName(value = "linhas_master", alternate = ["linha"])
    @Expose
    @PrimaryKey
    var code: String = "",
    var way: String = "",

    var workingDays: List<SchedulesDTO>? = null,

    var saturdays: List<SchedulesDTO>? = null,

    var sundays: List<SchedulesDTO>? = null

    )