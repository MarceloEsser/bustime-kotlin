package esser.marcelo.busoclock.vvm.home

import androidx.lifecycle.MutableLiveData
import android.content.Context
import androidx.lifecycle.ViewModel
import esser.marcelo.busoclock.dao.DaoHelper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    var favSize: MutableLiveData<Int> = MutableLiveData()
    lateinit var daoHelper: DaoHelper

    fun getLinesQuantity() {
        GlobalScope.launch {
            val size = daoHelper.getAll().size
            favSize.postValue(size)
        }
    }

    fun initializeDao(context: Context) {
        daoHelper = DaoHelper(context)
    }
}