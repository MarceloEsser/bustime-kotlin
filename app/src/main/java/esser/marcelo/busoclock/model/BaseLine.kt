package esser.marcelo.busoclock.model

import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import esser.marcelo.busoclock.model.schedules.BaseSchedule

@Entity
open class BaseLine(

    @PrimaryKey val lineId : Long,

    @SerializedName(value = "nome_master", alternate = ["nomeLinha"])
    @Expose
    @ColumnInfo(name = "line_name")
    var name: String = "",

    @SerializedName(value = "linhas_master", alternate = ["linha"])
    @Expose
    @ColumnInfo(name = "line_code")
    var code: String = "",
    var way: String = ""
    )