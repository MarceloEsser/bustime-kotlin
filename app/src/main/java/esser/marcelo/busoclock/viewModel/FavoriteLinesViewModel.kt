package esser.marcelo.busoclock.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import esser.marcelo.busoclock.repository.dao.DaoHelper
import esser.marcelo.busoclock.model.favorite.FavoriteLine
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/09/20
 */

class FavoriteLinesViewModel(
    private val daoHelper: DaoHelper
) : ViewModel() {

    private var mLines: MutableList<FavoriteLine> = mutableListOf()

    val favoriteLines: MutableLiveData<List<FavoriteLine>> = MutableLiveData()

    fun fillFavoriteLinesList() {
        GlobalScope.launch {
            val lines = daoHelper.getAll()
            lines.forEach {
                mLines.add(it.line!!)
            }
            favoriteLines.postValue(mLines)
        }
    }

    fun deleteAll() {
        GlobalScope.launch {
            daoHelper.deleteAll()
        }
    }
}