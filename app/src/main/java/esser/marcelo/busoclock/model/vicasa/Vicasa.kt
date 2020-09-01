package esser.marcelo.busoclock.model.vicasa

import esser.marcelo.busoclock.model.BaseLine

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/08/20
 */

class Vicasa(
    name: String = "", code: String = "", way: String  = ""
) : BaseLine(name = name, code = code, way = way) {

    override fun toString(): String {
        return name
    }

}