package esser.marcelo.busoclock.viewModel

import androidx.lifecycle.MutableLiveData
import android.content.Context
import androidx.lifecycle.ViewModel
import esser.marcelo.busoclock.dao.DaoHelper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeViewModel(private val daoHelper: DaoHelper): ViewModel() {

    var favSize: MutableLiveData<Int> = MutableLiveData()

    fun getLinesQuantity() {
        GlobalScope.launch {
            val size = daoHelper.getAll().size
            favSize.postValue(size)
        }
    }
}