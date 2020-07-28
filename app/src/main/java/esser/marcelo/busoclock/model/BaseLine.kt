package esser.marcelo.busoclock.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class BaseLine() {

    @SerializedName(value = "nome_master", alternate = ["nomeLinha"])
    @Expose
    var name: String = ""

    @SerializedName(value = "linhas_master", alternate = ["linha"])
    @Expose
    var code: String = ""
    var way: String = ""

    constructor(name: String, code: String, way: String) : this() {
        this.name = name
        this.code = code
        this.way = way
    }
}