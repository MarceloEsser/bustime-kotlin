package esser.marcelo.busoclock.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import esser.marcelo.busoclock.model.sogal.ItinerariesDTO
import esser.marcelo.busoclock.repository.dao.DaoHelper
import esser.marcelo.busoclock.repository.dao.LineDAO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoriteItinerariesViewModel(
    val daoHelper: DaoHelper,
    val dispatcher: CoroutineDispatcher
): ViewModel() {

    private val _itineraries: MutableLiveData<List<ItinerariesDTO>> = MutableLiveData()
    val itineraries: LiveData<List<ItinerariesDTO>> by lazy {
        fillFavoriteLinesList()

        return@lazy _itineraries
    }

    private fun fillFavoriteLinesList() = viewModelScope.launch(dispatcher) {
        daoHelper.getLines(LineDAO.lineId ?: 0).collect {
            if (it != null && it.isNotEmpty()) {
                val line = it[0]
                _itineraries.postValue(line.itineraries)
            }
        }
    }
}