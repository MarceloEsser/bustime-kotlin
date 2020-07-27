package esser.marcelo.busoclock.vvm.home

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.Log
import esser.marcelo.busoclock.dao.DaoHelper
import esser.marcelo.busoclock.model.favorite.SogalLineWithSchedules
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeViewModel() {

    val favSize = MutableLiveData<List<SogalLineWithSchedules>>()
    lateinit var daoHelper: DaoHelper

    fun getLinesQuantity() {
        favSize.value = daoHelper.getAll()
    }

    fun initializeDao(context: Context) {
        daoHelper = DaoHelper(context)
    }
}