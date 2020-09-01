package esser.marcelo.busoclock.helper

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/08/20
 */

object VicasaTableSelectors {
    const val WORKINGDAYS_BB: String =
        "body > table:nth-child(2) > tbody > tr > td > table.texto_linhas > tbody > tr:nth-child(5) > td:nth-child(1)"

    const val SATURDAYS_BB: String =
        "body > table:nth-child(2) > tbody > tr > td > table.texto_linhas > tbody > tr:nth-child(5) > td:nth-child(2)"

    const val SUNDAYS_BB: String =
        "body > table:nth-child(2) > tbody > tr > td > table.texto_linhas > tbody > tr:nth-child(5) > td:nth-child(3)"

    const val WORKINGDAYS_BC: String =
        "body > table:nth-child(2) > tbody > tr > td > table.texto_linhas > tbody > tr:nth-child(2) > td:nth-child(1)"

    const val SATURDAYS_BC: String =
        "body > table:nth-child(2) > tbody > tr > td > table.texto_linhas > tbody > tr:nth-child(2) > td:nth-child(2)"

    const val SUNDAYS_BC: String =
        "body > table:nth-child(2) > tbody > tr > td > table.texto_linhas > tbody > tr:nth-child(2) > td:nth-child(3)"

    const val WORKINGDAYS_CB: String =
        "body > table:nth-child(2) > tbody > tr > td > table.texto_linhas > tbody > tr:nth-child(2) > td:nth-child(4)"

    const val SATURDAYS_CB: String =
        "body > table:nth-child(2) > tbody > tr > td > table.texto_linhas > tbody > tr:nth-child(2) > td:nth-child(5)"

    const val SUNDAYS_CB: String =
        "body > table:nth-child(2) > tbody > tr > td > table.texto_linhas > tbody > tr:nth-child(2) > td:nth-child(6)"

    const val WORKINGDAYS_CC: String =
        "body > table:nth-child(2) > tbody > tr > td > table.texto_linhas > tbody > tr:nth-child(5) > td:nth-child(4)"

    const val SATURDAYS_CC: String =
        "body > table:nth-child(2) > tbody > tr > td > table.texto_linhas > tbody > tr:nth-child(5) > td:nth-child(5)"

    const val SUNDAYS_CC: String =
        "body > table:nth-child(2) > tbody > tr > td > table.texto_linhas > tbody > tr:nth-child(5) > td:nth-child(6)"
}