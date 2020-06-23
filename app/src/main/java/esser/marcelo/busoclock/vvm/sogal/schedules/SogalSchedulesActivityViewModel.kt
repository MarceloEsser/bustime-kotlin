package esser.marcelo.busoclock.vvm.sogal.schedules

import esser.marcelo.busoclock.dao.LineDAO
import esser.marcelo.busoclock.model.schedules.BaseSchedule
import esser.marcelo.busoclock.model.sogal.SogalResponse
import esser.marcelo.busoclock.service.sogalServices.ISogalService
import esser.marcelo.busoclock.service.sogalServices.SogalService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author Marcelo Esser
 * @since 12/03/19
 */
class SogalSchedulesActivityViewModel {

    var workingDays: List<BaseSchedule>? = null
    var saturdays: List<BaseSchedule>? = null
    var sundays: List<BaseSchedule>? = null

    private val sogalService: ISogalService by lazy {
        SogalService().sogalSerivce()
    }

    fun loadSchedulesBy(
        onSuccess: (schedules: List<BaseSchedule>) -> Unit,
        onError: (errorMessage: String) -> Unit
    ) {
        sogalService.getSogalSchedulesBy(lineWay = LineDAO.lineWay!!.way, lineCode = LineDAO.lineCode)
            .enqueue(object : Callback<SogalResponse> {
                override fun onFailure(call: Call<SogalResponse>, t: Throwable) {
                    onError(t.message.toString())
                }

                override fun onResponse(call: Call<SogalResponse>, response: Response<SogalResponse>) {
                    response.body()?.let { sogalResponse ->
                        onSuccess(sogalResponse.workingDays!!)
                        workingDays = sogalResponse.workingDays
                        saturdays = sogalResponse.saturdays
                        sundays = sogalResponse.sundays
                    }
                }

            })
    }

}