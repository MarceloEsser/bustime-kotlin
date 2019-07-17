package esser.marcelo.busoclock.vvm.sogal.schedules

import esser.marcelo.busoclock.dao.LinesDAO
import esser.marcelo.busoclock.model.sogal.SchedulesDTO
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

    var workingDays: List<SchedulesDTO>? = null
    var saturdays: List<SchedulesDTO>? = null
    var sundays: List<SchedulesDTO>? = null

    private val sogalService: ISogalService by lazy {
        SogalService().sogalSerivce()
    }

    fun loadSchedulesBy(
        onSuccess: (response: SogalResponse) -> Unit,
        onError: (errorMessage: String) -> Unit
    ) {
        val lineWay: String = LinesDAO.lineWay
        val lineCode: String = LinesDAO.lineCode

        sogalService.getSogalSchedulesBy(lineWay = lineWay, lineCode = lineCode)
            .enqueue(object : Callback<SogalResponse> {
                override fun onFailure(call: Call<SogalResponse>, t: Throwable) {
                    onError(t.message.toString())
                }

                override fun onResponse(call: Call<SogalResponse>, response: Response<SogalResponse>) {
                    response.body()?.let { sogalResponse ->
                        onSuccess(sogalResponse)
                        workingDays = sogalResponse.workingDays
                        saturdays = sogalResponse.saturdays
                        sundays = sogalResponse.sundays
                    }

                }

            })
    }

}