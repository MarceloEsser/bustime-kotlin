package esser.marcelo.busoclock.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import esser.marcelo.busoclock.repository.dao.DaoHelper
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

class HomeViewModel(
    private val daoHelper: DaoHelperDelegate,
    private val dispatcher: CoroutineContext
) : ViewModel() {

    var favSize: MutableLiveData<Int> = MutableLiveData()

    fun getLinesQuantity() = viewModelScope.launch(dispatcher) {
        daoHelper.getAll().collect {
                favSize.postValue(it?.size)
        }
    }
}