package esser.marcelo.busoclock.model

import org.jsoup.nodes.Entities.escape

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/08/20
 */

object Constants {

    //Country constants
    const val CACHOEIRINHA: String = "CACHOEIRINHA"
    const val CANOAS: String = "CANOAS"
    val GRAVATAI: String = "GRAVATA%CD"
    const val POA: String = "PORTO ALEGRE"

    //Service type ids
    const val TODOS: String = "T"
    const val COMUM: String = "C"
    const val EXECUTIVO: String = "E"
    const val INTEGRACAO: String = "I"
    const val METROPOLITANA: String = "M"
    const val ROTAS: String = "R"
    const val SELETIVA: String = "S"
    const val URBANA: String = "U"

    //line ways
    const val CB_WAY = "buscaHorarioLinhaCB"
    const val BC_WAY = "buscaHorarioLinhaBC"
    const val CC_WAY = "centroCircular"
    const val BB_WAY = "bairroCircular"

    //line ways description (adapter row)
    const val WAY_CC_DESCRIPTION = "Centro Circular - CC"
    const val WAY_BB_DESCRIPTION = "Bairro Circular - BB"
    const val WAY_CB_DESCRIPTION = "Centro Bairro - CB"
    const val WAT_BC_DESCRIPTION = "Bairro Centro - BC"
}