package esser.marcelo.busoclock.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import esser.marcelo.busoclock.repository.dao.database.BusTimeDao
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

class HomeViewModel(
    private val dao: BusTimeDao,
    private val dispatcher: CoroutineContext
) : ViewModel() {

    val _favSize: MutableLiveData<Int> = MutableLiveData()
    val favSize: LiveData<Int> by lazy {
        getLinesQuantity()
        return@lazy _favSize
    }

    fun getLinesQuantity() = viewModelScope.launch(dispatcher) {
        dao.getAll().collect {
            _favSize.postValue(it?.size)
        }
    }
}