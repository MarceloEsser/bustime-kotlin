package marcelo.esser.com.bustimek.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class LineCodeLineName(
    @SerializedName(value = "nome_master", alternate = ["nomeLinha"])
    @Expose
    var name: String = "",

    @SerializedName(value = "linhas_master", alternate = ["linha"])
    @Expose
    var code: String = ""
)