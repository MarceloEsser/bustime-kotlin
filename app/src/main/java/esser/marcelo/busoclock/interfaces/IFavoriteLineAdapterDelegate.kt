package esser.marcelo.busoclock.interfaces

import esser.marcelo.busoclock.model.favorite.FavoriteLine

interface IFavoriteLineAdapterDelegate {
    fun onLineClicked(line: FavoriteLine)
}