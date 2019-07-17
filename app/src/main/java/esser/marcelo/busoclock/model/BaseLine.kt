package esser.marcelo.busoclock.model

import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import esser.marcelo.busoclock.model.sogal.SchedulesDTO

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