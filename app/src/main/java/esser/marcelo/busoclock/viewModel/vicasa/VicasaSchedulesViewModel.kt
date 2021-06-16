package esser.marcelo.busoclock.viewModel.vicasa

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import esser.marcelo.busoclock.interfaces.SaveLineDelegate
import esser.marcelo.busoclock.repository.LineHolder
import esser.marcelo.busoclock.model.Constants.BB_WAY
import esser.marcelo.busoclock.model.Constants.BC_WAY
import esser.marcelo.busoclock.model.Constants.CB_WAY
import esser.marcelo.busoclock.model.Constants.CC_WAY
import esser.marcelo.busoclock.model.VicasaTableSelectors
import esser.marcelo.busoclock.model.favorite.FavoriteLine
import esser.marcelo.busoclock.model.schedules.Saturday
import esser.marcelo.busoclock.model.schedules.Sunday
import esser.marcelo.busoclock.model.schedules.Workingday
import esser.marcelo.busoclock.repository.dao.helper.DaoHelper
import esser.marcelo.busoclock.repository.service.vicasaServices.VicasaServiceDelegate
import esser.marcelo.busoclock.repository.service.wrapper.resource.Status
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import kotlin.coroutines.CoroutineContext

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/09/20
 */

class VicasaSchedulesViewModel(
    private val daoHelper: DaoHelper,
    private val service: VicasaServiceDelegate,
    private val dispatcher: CoroutineContext
) : ViewModel() {

    init {
        daoHelper.vicasaSchedulesViewModel = this
    }

    var saverDelegate: SaveLineDelegate? = null

    private val _workingDays = MutableLiveData<List<Workingday>>()
    val workingdays: LiveData<List<Workingday>> by lazy {
        loadSchedules()
        return@lazy _workingDays
    }

    private val _saturdays = MutableLiveData<List<Saturday>>()
    val saturdays: LiveData<List<Saturday>>
        get() = _saturdays

    private val _sundays = MutableLiveData<List<Sunday>>()
    val sundays: LiveData<List<Sunday>>
        get() = _sundays

    private val _isLineFavorite: MutableLiveData<Boolean> = MutableLiveData()
    val isLineFavorite: LiveData<Boolean> by lazy {
        validateLine()
        return@lazy _isLineFavorite
    }

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private var workingdaysElement: Element? = null
    private var saturdaysElement: Element? = null
    private var sundaysElement: Element? = null


    fun loadSchedules() {
        viewModelScope.launch(dispatcher) {
            service.getSchedules(LineHolder.lineCode).collect { resource ->
                when (resource.requestStatus) {
                    Status.success -> {
                        if (resource.data != null) {
                            parseResponse(resource.data.string())
                        }
                    }
                    Status.error -> _error.postValue(resource.message)
                }

            }
        }
    }

    private suspend fun parseResponse(response: String) = viewModelScope.launch(dispatcher) {

        setElements(response)

        val workingdaysListAsync = async { fillWorkingdays(workingdaysElement) }
        val saturdaysListAsync = async { fillSaturdays(saturdaysElement) }
        val sundaysListAsync = async { fillSundays(sundaysElement) }

        setAllCompletedList(
            workingdaysListAsync.await(),
            saturdaysListAsync.await(),
            sundaysListAsync.await(),
        )

    }

    private fun setElements(response: String) {
        val document: Document = Jsoup.parse(response)

        when (LineHolder.lineWay!!.way) {
            CC_WAY -> fillCCElements(document)
            BB_WAY -> fillBBElements(document)
            BC_WAY -> fillBCElements(document)
            CB_WAY -> fillCBElements(document)
        }
    }

    private fun fillCCElements(document: Document) {
        workingdaysElement = document.selectFirst(VicasaTableSelectors.WORKINGDAYS_CC)
        saturdaysElement = document.selectFirst(VicasaTableSelectors.SATURDAYS_CC)
        sundaysElement = document.selectFirst(VicasaTableSelectors.SUNDAYS_CC)
    }

    private fun fillBCElements(document: Document) {
        workingdaysElement = document.selectFirst(VicasaTableSelectors.WORKINGDAYS_BC)
        saturdaysElement = document.selectFirst(VicasaTableSelectors.SATURDAYS_BC)
        sundaysElement = document.selectFirst(VicasaTableSelectors.SUNDAYS_BC)
    }

    private fun fillBBElements(document: Document) {
        workingdaysElement = document.selectFirst(VicasaTableSelectors.WORKINGDAYS_BB)
        saturdaysElement = document.selectFirst(VicasaTableSelectors.SATURDAYS_BB)
        sundaysElement = document.selectFirst(VicasaTableSelectors.SUNDAYS_BB)
    }

    private fun fillCBElements(document: Document) {
        workingdaysElement = document.selectFirst(VicasaTableSelectors.WORKINGDAYS_CB)
        saturdaysElement = document.selectFirst(VicasaTableSelectors.SATURDAYS_CB)
        sundaysElement = document.selectFirst(VicasaTableSelectors.SUNDAYS_CB)
    }

    private fun fillSundays(element: Element?): MutableList<Sunday> {
        val schedulesList: MutableList<Sunday> = ArrayList()

        element?.apply {
            for (mElement in children()) {
                val schedule = mElement.text()
                val sunday = Sunday()
                sunday.hour = schedule
                schedulesList.add(sunday)
            }
        }

        return schedulesList
    }

    private fun fillSaturdays(element: Element?): MutableList<Saturday> {
        val schedulesList: MutableList<Saturday> = ArrayList()

        element?.apply {
            for (mElement in children()) {
                val schedule = mElement.text()
                val saturday = Saturday()
                saturday.hour = schedule
                schedulesList.add(saturday)
            }
        }

        return schedulesList
    }

    private fun fillWorkingdays(element: Element?): MutableList<Workingday> {
        val schedulesList: MutableList<Workingday> = ArrayList()

        element?.apply {
            for (mElement in children()) {
                val schedule = mElement.text()
                val workingday = Workingday()
                workingday.hour = schedule
                schedulesList.add(workingday)
            }
        }

        return schedulesList
    }

    private fun setAllCompletedList(
        workingdaysList: MutableList<Workingday>,
        saturdaysList: MutableList<Saturday>,
        sundaysList: MutableList<Sunday>,
    ) {

        this._workingDays.postValue(workingdaysList)
        this._saturdays.postValue(saturdaysList)
        this._sundays.postValue(sundaysList)

        saveLineDelegate()
    }

    private fun saveLineDelegate() {
        if (this.saverDelegate != null) {
            saverDelegate?.canInsertSchedules(isSogal = false)
        }
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
            isSogal = false,
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