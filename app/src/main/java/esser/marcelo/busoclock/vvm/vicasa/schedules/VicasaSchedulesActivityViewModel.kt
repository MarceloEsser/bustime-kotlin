package esser.marcelo.busoclock.vvm.vicasa.schedules

import esser.marcelo.busoclock.dao.LineDAO
import esser.marcelo.busoclock.helper.Constants.CB_WAY
import esser.marcelo.busoclock.model.sogal.SchedulesDTO
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

    private val WORKINGDAYS_BC_SELECTOR: String =
        "body > table:nth-child(2) > tbody > tr > td > table.texto_linhas > tbody > tr:nth-child(5) > td:nth-child(1)"

    private val SATURDAYS_BC_SELECTOR: String =
        "body > table:nth-child(2) > tbody > tr > td > table.texto_linhas > tbody > tr:nth-child(5) > td:nth-child(2)"

    private val SUNDAYS_BC_SELECTOR: String =
        "body > table:nth-child(2) > tbody > tr > td > table.texto_linhas > tbody > tr:nth-child(5) > td:nth-child(3)"

    private val WORKINGDAYS_CB_SELECTOR: String =
        "body > table:nth-child(2) > tbody > tr > td > table.texto_linhas > tbody > tr:nth-child(5) > td:nth-child(4)"

    private val SATURDAYS_CB_SELECTOR: String =
        "body > table:nth-child(2) > tbody > tr > td > table.texto_linhas > tbody > tr:nth-child(5) > td:nth-child(5)"

    private val SUNDAYS_CB_SELECTOR: String =
        "body > table:nth-child(2) > tbody > tr > td > table.texto_linhas > tbody > tr:nth-child(5) > td:nth-child(6)"

    var workingdaysList: MutableList<SchedulesDTO> = ArrayList()
    var saturdaysList: MutableList<SchedulesDTO> = ArrayList()
    var sundaysList: MutableList<SchedulesDTO> = ArrayList()

    var workingdaysElement: Element? = null
    var saturdaysElement: Element? = null
    var sundaysElement: Element? = null

    private val vicasaService: VicasaService by lazy {
        VicasaService()
    }

    @ExperimentalCoroutinesApi
    fun loadSchedules(
        onSuccess: (schedules: List<SchedulesDTO>) -> Unit,
        onError: (errorMessage: String) -> Unit
    ) {
        vicasaService.vicasaService().getVicasaSchedules(LineDAO.lineCode).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onError(t.message!!)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                parseResponse(response.body()!!.string(), onSuccess)
            }
        })
    }

    @ExperimentalCoroutinesApi
    fun parseResponse(response: String, onSuccess: (schedules: List<SchedulesDTO>) -> Unit) = runBlocking {
        val document: Document = Jsoup.parse(response)

        if (LineDAO.lineWay == CB_WAY) {
            fillCBElements(document)
        } else {
            fillBCElements(document)
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

    private fun fillCBElements(document: Document) {
        workingdaysElement = document.selectFirst(WORKINGDAYS_CB_SELECTOR)
        saturdaysElement = document.selectFirst(SATURDAYS_CB_SELECTOR)
        sundaysElement = document.selectFirst(SUNDAYS_CB_SELECTOR)
    }

    private fun fillBCElements(document: Document) {
        workingdaysElement = document.selectFirst(WORKINGDAYS_BC_SELECTOR)
        saturdaysElement = document.selectFirst(SATURDAYS_BC_SELECTOR)
        sundaysElement = document.selectFirst(SUNDAYS_BC_SELECTOR)
    }

    fun getSchedulesListBy(element: Element?): MutableList<SchedulesDTO> {
        val schedulesList: MutableList<SchedulesDTO> = ArrayList()

        for (sarturdayElement in element!!.children()) {
            val schedule = sarturdayElement.text()
            schedulesList.add(SchedulesDTO(hour = schedule))
        }
        return schedulesList
    }

    private fun getAllCompletedList(
        workingdaysList: MutableList<SchedulesDTO>,
        saturdaysList: MutableList<SchedulesDTO>,
        sundaysList: MutableList<SchedulesDTO>,
        onSuccess: (schedules: List<SchedulesDTO>) -> Unit
    ) {
        this.workingdaysList = workingdaysList
        this.saturdaysList = saturdaysList
        this.sundaysList = sundaysList

        onSuccess(this.workingdaysList)

    }
}