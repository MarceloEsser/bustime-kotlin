package esser.marcelo.busoclock.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import esser.marcelo.busoclock.model.favorite.LineWithSchedules
import esser.marcelo.busoclock.repository.dao.DaoHelperDelegate
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/09/20
 */

class FavoriteLinesViewModel(
    private val daoHelper: DaoHelperDelegate,
    private val dispatcher: CoroutineContext
) : ViewModel() {

    private val _favoriteLines: MutableLiveData<List<LineWithSchedules>> = MutableLiveData()
    val favoriteLines: LiveData<List<LineWithSchedules>> by lazy {
        fillFavoriteLinesList()

        return@lazy _favoriteLines
    }

    private fun fillFavoriteLinesList() = viewModelScope.launch(dispatcher) {
        daoHelper.getAll().collect { lines ->
            if (lines != null)
                _favoriteLines.postValue(lines)
        }
    }

    fun deleteAll() {
        GlobalScope.launch {
            daoHelper.clearDatabase()
        }
    }
}