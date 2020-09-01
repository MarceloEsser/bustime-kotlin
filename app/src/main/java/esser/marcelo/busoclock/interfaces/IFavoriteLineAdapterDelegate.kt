package esser.marcelo.busoclock.interfaces

import esser.marcelo.busoclock.model.favorite.FavoriteLine

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/08/20
 */

interface IFavoriteLineAdapterDelegate {
    fun onLineClicked(line: FavoriteLine)
}