package esser.marcelo.busoclock.vvm.favorite

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import esser.marcelo.busoclock.dao.DaoHelper
import esser.marcelo.busoclock.model.favorite.FavoriteLine
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoriteLinesViewModel : ViewModel() {

    private lateinit var daoHelper: DaoHelper

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

    fun initializeDao(context: Context) {
        this.daoHelper = DaoHelper(context)
    }

    fun deleteAll() {
        GlobalScope.launch {
            daoHelper.deleteAll()
        }
    }
}