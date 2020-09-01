package esser.marcelo.busoclock.interfaces

import esser.marcelo.busoclock.model.BaseLine

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/08/20
 */

interface GenericLinesAdapterDelegate {
    fun onItemClickLitener(line: BaseLine)
}