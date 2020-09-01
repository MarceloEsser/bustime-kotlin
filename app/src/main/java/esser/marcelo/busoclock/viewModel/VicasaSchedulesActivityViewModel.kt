package esser.marcelo.busoclock.viewModel

import esser.marcelo.busoclock.dao.LineDAO
import esser.marcelo.busoclock.helper.Constants.BB_WAY
import esser.marcelo.busoclock.helper.Constants.BC_WAY
import esser.marcelo.busoclock.helper.Constants.CB_WAY
import esser.marcelo.busoclock.helper.Constants.CC_WAY
import esser.marcelo.busoclock.helper.VicasaTableSelectors
import esser.marcelo.busoclock.model.schedules.BaseSchedule
import esser.marcelo.busoclock.service.vicasaServices.VicasaService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VicasaSchedulesActivityViewModel {

    var workingdaysList: MutableList<BaseSchedule> = ArrayList()
    var saturdaysList: MutableList<BaseSchedule> = ArrayList()
    var sundaysList: MutableList<BaseSchedule> = ArrayList()

    private var workingdaysElement: Element? = null
    private var saturdaysElement: Element? = null
    private var sundaysElement: Element? = null

    private val vicasaService: VicasaService by lazy {
        VicasaService()
    }

    fun loadSchedules(
        onSuccess: (schedules: List<BaseSchedule>) -> Unit,
        onError: (errorMessage: String) -> Unit
    ) {
//        vicasaService.vicasaService().getVicasaSchedules(LineDAO.lineCode)
//            .enqueue(object : Callback<ResponseBody> {
//                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                    onError(t.message!!)
//                }
//
//                override fun onResponse(
//                    call: Call<ResponseBody>,
//                    response: Response<ResponseBody>
//                ) {
//                    parseResponse(response.body()!!.string(), onSuccess)
//                }
//            })
    }

    fun parseResponse(response: String, onSuccess: (schedules: List<BaseSchedule>) -> Unit) =
        runBlocking {
            val document: Document = Jsoup.parse(response)

            when (LineDAO.lineWay!!.way) {
                CC_WAY -> fillCCElements(document)
                BB_WAY -> fillBBElements(document)
                BC_WAY -> fillBCElements(document)
                CB_WAY -> fillCBElements(document)
            }

            val workingdaysListAsync = async { getSchedulesListBy(workingdaysElement) }
            val saturdaysListAsync = async { getSchedulesListBy(saturdaysElement) }
            val sundaysListAsync = async { getSchedulesListBy(sundaysElement) }

            getAllCompletedList(
                workingdaysListAsync.await(),
                saturdaysListAsync.await(),
                sundaysListAsync.await(),
                onSuccess
            )
        }

    private fun fillCCElements(document: Document) {
        workingdaysElement = document.selectFirst(VicasaTableSelectors.WORKINGDAYS_CC_SELECTOR)
        saturdaysElement = document.selectFirst(VicasaTableSelectors.SATURDAYS_CC_SELECTOR)
        sundaysElement = document.selectFirst(VicasaTableSelectors.SUNDAYS_CC_SELECTOR)
    }

    private fun fillBCElements(document: Document) {
        workingdaysElement = document.selectFirst(VicasaTableSelectors.WORKINGDAYS_BC_SELECTOR)
        saturdaysElement = document.selectFirst(VicasaTableSelectors.SATURDAYS_BC_SELECTOR)
        sundaysElement = document.selectFirst(VicasaTableSelectors.SUNDAYS_BC_SELECTOR)
    }

    private fun fillBBElements(document: Document) {
        workingdaysElement = document.selectFirst(VicasaTableSelectors.WORKINGDAYS_BB_SELECTOR)
        saturdaysElement = document.selectFirst(VicasaTableSelectors.SATURDAYS_BB_SELECTOR)
        sundaysElement = document.selectFirst(VicasaTableSelectors.SUNDAYS_BB_SELECTOR)
    }

    private fun fillCBElements(document: Document) {
        workingdaysElement = document.selectFirst(VicasaTableSelectors.WORKINGDAYS_CB_SELECTOR)
        saturdaysElement = document.selectFirst(VicasaTableSelectors.SATURDAYS_CB_SELECTOR)
        sundaysElement = document.selectFirst(VicasaTableSelectors.SUNDAYS_CB_SELECTOR)
    }

    fun getSchedulesListBy(element: Element?): MutableList<BaseSchedule> {
        val schedulesList: MutableList<BaseSchedule> = ArrayList()

        for (sarturdayElement in element!!.children()) {
            val schedule = sarturdayElement.text()
            schedulesList.add(
                BaseSchedule(
                    hour = schedule
                )
            )
        }
        return schedulesList
    }

    private fun getAllCompletedList(
        workingdaysList: MutableList<BaseSchedule>,
        saturdaysList: MutableList<BaseSchedule>,
        sundaysList: MutableList<BaseSchedule>,
        onSuccess: (schedules: List<BaseSchedule>) -> Unit
    ) {
        this.workingdaysList = workingdaysList
        this.saturdaysList = saturdaysList
        this.sundaysList = sundaysList

        onSuccess(this.workingdaysList)

    }
}