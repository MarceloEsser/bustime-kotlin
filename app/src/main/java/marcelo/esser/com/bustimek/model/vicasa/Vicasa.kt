package marcelo.esser.com.bustimek.model.vicasa

import marcelo.esser.com.bustimek.model.LineCodeLineName

class Vicasa(
    name: String = "", conde: String = ""
) : LineCodeLineName(name = name, code = conde) {

    override fun toString(): String {
        return name
    }

}