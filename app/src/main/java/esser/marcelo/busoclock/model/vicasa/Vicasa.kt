package esser.marcelo.busoclock.model.vicasa

import esser.marcelo.busoclock.model.BaseLine

class Vicasa(
    name: String = "", code: String = "", way: String  = ""
) : BaseLine(name = name, code = code, way = way) {

    override fun toString(): String {
        return name
    }

}