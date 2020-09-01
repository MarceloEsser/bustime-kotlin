package esser.marcelo.busoclock.interfaces

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/08/20
 */

interface FilterDialogInteraction {
    fun doFilter(countryOrigin: String, countryDestination: String, serviceType: String)
}