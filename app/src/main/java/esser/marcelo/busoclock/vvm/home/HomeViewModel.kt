package esser.marcelo.busoclock.vvm.home

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.Log
import esser.marcelo.busoclock.dao.DaoHelper
import esser.marcelo.busoclock.model.favorite.SogalLineWithSchedules
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeViewModel() {

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