package marcelo.esser.com.bustimek.vvm.sogal.schedules

import marcelo.esser.com.bustimek.dao.DataOnHold
import marcelo.esser.com.bustimek.extensions.box
import marcelo.esser.com.bustimek.model.sogal.SchedulesDTO
import marcelo.esser.com.bustimek.model.sogal.SogalFavoriteLine
import marcelo.esser.com.bustimek.model.sogal.SogalResponse
import marcelo.esser.com.bustimek.service.sogalServices.ISogalService
import marcelo.esser.com.bustimek.service.sogalServices.SogalService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author Marcelo Esser
 * @since 12/03/19
 */
class SchedulesActivityViewModel {

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

        val lineWay: String? = DataOnHold().lineWay
        val lineCode: String? = DataOnHold().lineCode

        sogalService.getSogalSchedulesBy(lineWay!!, lineCode!!).enqueue(object : Callback<SogalResponse> {
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

    fun addOrRemoveLine() {
        box<SogalFavoriteLine>().put(
            SogalFavoriteLine(
                workingDays = workingDays,
                saturdays = saturdays,
                sundays = sundays,
                lineCode = DataOnHold().lineCode,
                lineName = DataOnHold().lineName,
                lineWay = DataOnHold().lineWay
            )
        )
    }
}