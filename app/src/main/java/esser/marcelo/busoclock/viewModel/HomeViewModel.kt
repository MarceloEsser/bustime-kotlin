package esser.marcelo.busoclock.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import esser.marcelo.busoclock.repository.dao.DaoHelper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/09/20
 */

class HomeViewModel(private val daoHelper: DaoHelper): ViewModel() {

    var favSize: MutableLiveData<Int> = MutableLiveData()

    fun getLinesQuantity() {
        GlobalScope.launch {
            val size = daoHelper.getAll().size
            favSize.postValue(size)
        }
    }
}