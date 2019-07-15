package marcelo.esser.com.bustimek.model.vicasa

import marcelo.esser.com.bustimek.model.LineCodeLineName

class Vicasa(
    name: String = "", lineCode: String = ""
) : LineCodeLineName(name = name, code = lineCode) {

    override fun toString(): String {
        return name
    }

}