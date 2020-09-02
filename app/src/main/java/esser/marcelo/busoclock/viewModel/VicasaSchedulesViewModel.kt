package esser.marcelo.busoclock.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import esser.marcelo.busoclock.repository.dao.LineDAO
import esser.marcelo.busoclock.model.Constants.BB_WAY
import esser.marcelo.busoclock.model.Constants.BC_WAY
import esser.marcelo.busoclock.model.Constants.CB_WAY
import esser.marcelo.busoclock.model.Constants.CC_WAY
import esser.marcelo.busoclock.model.VicasaTableSelectors
import esser.marcelo.busoclock.model.schedules.BaseSchedule
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
    private val service: VicasaServiceDelegate,
    private val dispatcher: CoroutineContext
) : ViewModel() {

    private val _workingdays = MutableLiveData<List<BaseSchedule>>()
    val workingdays: LiveData<List<BaseSchedule>> by lazy {
        loadSchedules()
        return@lazy _workingdays
    }

    private val _saturdays = MutableLiveData<List<BaseSchedule>>()
    val saturdays: LiveData<List<BaseSchedule>>
        get() = _saturdays

    private val _sundays = MutableLiveData<List<BaseSchedule>>()
    val sundays: LiveData<List<BaseSchedule>>
        get() = _sundays


    private var workingdaysElement: Element? = null
    private var saturdaysElement: Element? = null
    private var sundaysElement: Element? = null


    fun loadSchedules() = viewModelScope.launch(dispatcher) {

        service.getSchedules(LineDAO.lineCode).collect { resource ->
            if (resource.requestStatus == Status.success && resource.data != null) {
                val htmlBody = async { resource.data.string() }
                parseResponse(htmlBody.await())
            }
        }
    }

    private fun parseResponse(response: String) = viewModelScope.launch(dispatcher) {

        setElements(response)

        val workingdaysListAsync = async { getSchedulesFrom(workingdaysElement) }
        val saturdaysListAsync = async { getSchedulesFrom(saturdaysElement) }
        val sundaysListAsync = async { getSchedulesFrom(sundaysElement) }

        setAllCompletedList(
            workingdaysListAsync.await(),
            saturdaysListAsync.await(),
            sundaysListAsync.await(),
        )
    }

    private fun setElements(response: String) {
        val document: Document = Jsoup.parse(response)

        when (LineDAO.lineWay!!.way) {
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

    private fun getSchedulesFrom(element: Element?): MutableList<BaseSchedule> {
        val schedulesList: MutableList<BaseSchedule> = ArrayList()

        element?.apply {
            for (mElement in children()) {
                val schedule = mElement.text()
                schedulesList.add(
                    BaseSchedule(
                        hour = schedule
                    )
                )
            }
        }

        return schedulesList
    }

    private fun setAllCompletedList(
        workingdaysList: MutableList<BaseSchedule>,
        saturdaysList: MutableList<BaseSchedule>,
        sundaysList: MutableList<BaseSchedule>,
    ) {
        this._workingdays.postValue(workingdaysList)
        this._saturdays.postValue(saturdaysList)
        this._sundays.postValue(sundaysList)
    }
}