package esser.marcelo.busoclock.helper

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/08/20
 */

object VicasaTableSelectors {
    val WORKINGDAYS_BB_SELECTOR: String =
        "body > table:nth-child(2) > tbody > tr > td > table.texto_linhas > tbody > tr:nth-child(5) > td:nth-child(1)"

    val SATURDAYS_BB_SELECTOR: String =
        "body > table:nth-child(2) > tbody > tr > td > table.texto_linhas > tbody > tr:nth-child(5) > td:nth-child(2)"

    val SUNDAYS_BB_SELECTOR: String =
        "body > table:nth-child(2) > tbody > tr > td > table.texto_linhas > tbody > tr:nth-child(5) > td:nth-child(3)"

    val WORKINGDAYS_BC_SELECTOR: String =
        "body > table:nth-child(2) > tbody > tr > td > table.texto_linhas > tbody > tr:nth-child(2) > td:nth-child(1)"

    val SATURDAYS_BC_SELECTOR: String =
        "body > table:nth-child(2) > tbody > tr > td > table.texto_linhas > tbody > tr:nth-child(2) > td:nth-child(2)"

    val SUNDAYS_BC_SELECTOR: String =
        "body > table:nth-child(2) > tbody > tr > td > table.texto_linhas > tbody > tr:nth-child(2) > td:nth-child(3)"

    val WORKINGDAYS_CB_SELECTOR: String =
        "body > table:nth-child(2) > tbody > tr > td > table.texto_linhas > tbody > tr:nth-child(2) > td:nth-child(4)"

    val SATURDAYS_CB_SELECTOR: String =
        "body > table:nth-child(2) > tbody > tr > td > table.texto_linhas > tbody > tr:nth-child(2) > td:nth-child(5)"

    val SUNDAYS_CB_SELECTOR: String =
        "body > table:nth-child(2) > tbody > tr > td > table.texto_linhas > tbody > tr:nth-child(2) > td:nth-child(6)"

    val WORKINGDAYS_CC_SELECTOR: String =
        "body > table:nth-child(2) > tbody > tr > td > table.texto_linhas > tbody > tr:nth-child(5) > td:nth-child(4)"

    val SATURDAYS_CC_SELECTOR: String =
        "body > table:nth-child(2) > tbody > tr > td > table.texto_linhas > tbody > tr:nth-child(5) > td:nth-child(5)"

    val SUNDAYS_CC_SELECTOR: String =
        "body > table:nth-child(2) > tbody > tr > td > table.texto_linhas > tbody > tr:nth-child(5) > td:nth-child(6)"
}