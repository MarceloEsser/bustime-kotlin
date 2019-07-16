package marcelo.esser.com.bustimek.model.vicasa

import marcelo.esser.com.bustimek.model.BaseLine

class Vicasa(
    name: String = "", code: String = "", way: String  = ""
) : BaseLine(name = name, code = code, way = way) {

    override fun toString(): String {
        return name
    }

}