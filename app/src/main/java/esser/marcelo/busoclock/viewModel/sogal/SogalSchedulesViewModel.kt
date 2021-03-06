package esser.marcelo.busoclock.viewModel.sogal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import esser.marcelo.busoclock.interfaces.SaveLineDelegate
import esser.marcelo.busoclock.model.favorite.FavoriteLine
import esser.marcelo.busoclock.repository.LineHolder
import esser.marcelo.busoclock.model.schedules.Saturday
import esser.marcelo.busoclock.model.schedules.Sunday
import esser.marcelo.busoclock.model.schedules.Workingday
import esser.marcelo.busoclock.model.sogal.SogalResponse
import esser.marcelo.busoclock.repository.dao.helper.DaoHelper
import esser.marcelo.busoclock.repository.service.sogalServices.SogalServiceDelegate
import esser.marcelo.busoclock.repository.service.wrapper.resource.Resource
import esser.marcelo.busoclock.repository.service.wrapper.resource.Status
import kotlinx.android.synthetic.main.activity_schedules.*
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

class SogalSchedulesViewModel(
    private val daoHelper: DaoHelper,
    private val service: SogalServiceDelegate,
    private val dispatcher: CoroutineContext
) : ViewModel() {

    init {
        daoHelper.sogalSchedulesViewModel = this
    }

    private val _isLineFavorite: MutableLiveData<Boolean> = MutableLiveData()
    val isLineFavorite: LiveData<Boolean> by lazy {
        validateLine()
        return@lazy _isLineFavorite
    }

    var saverDelegate: SaveLineDelegate? = null

    private val _workingDays = MutableLiveData<List<Workingday>>()
    val workingdays: LiveData<List<Workingday>> by lazy {
        loadSchedules()
        return@lazy _workingDays
    }

    private val _saturdays = MutableLiveData<List<Saturday>>()
    val saturdays: LiveData<List<Saturday>> by lazy {
        return@lazy _saturdays
    }

    private val _sundays = MutableLiveData<List<Sunday>>()
    val sundays: LiveData<List<Sunday>> by lazy {
        return@lazy _sundays
    }

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error


    fun loadSchedules() {
        viewModelScope.launch(dispatcher) {
            service.getSchedules(LineHolder.lineWay?.way ?: "", LineHolder.lineCode).collect { resource ->
                when (resource.requestStatus) {
                    Status.success -> {
                        fillSchedules(resource)
                        saveLineDelegate()
                    }
                    Status.error -> _error.postValue(resource.message)
                }
            }
        }
    }

    private fun saveLineDelegate() {
        if (saverDelegate != null) {
            saverDelegate?.canInsertSchedules(isSogal = true)
        }
    }

    private fun fillSchedules(resource: Resource<SogalResponse?>) {
        _workingDays.postValue(resource.data?.workingDays)
        _saturdays.postValue(resource.data?.saturdays)
        _sundays.postValue(resource.data?.sundays)
    }

    private fun validateLine() {
        viewModelScope.launch(dispatcher) {
            daoHelper.getLines(
                LineHolder.lineName,
                LineHolder.lineCode,
                LineHolder.lineWay?.description ?: ""
            ).collect {
                _isLineFavorite.postValue(it?.size == 1)
            }
        }
    }

    private fun getFavoriteLine(): FavoriteLine {
        return FavoriteLine(
            isSogal = true,
            name = LineHolder.lineName,
            code = LineHolder.lineCode,
            way = LineHolder.lineWay?.description ?: ""
        )
    }

    fun saveLine() = viewModelScope.launch(dispatcher) {
        val favoriteLine: FavoriteLine = getFavoriteLine()
        daoHelper.workingdays = _workingDays.value ?: listOf()
        daoHelper.saturdays = saturdays.value ?: listOf()
        daoHelper.sundays = sundays.value ?: listOf()
        daoHelper.insertLine(favoriteLine)
        validateLine()
    }

    fun deleteLine() {
        viewModelScope.launch(dispatcher) {
            daoHelper.deleteLine(
                lineName = LineHolder.lineName,
                lineCode = LineHolder.lineCode,
                lineWayDescription = LineHolder.lineWay?.description ?: ""
            )
            validateLine()
        }
    }
}
