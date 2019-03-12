package marcelo.esser.com.bustimek.vvm.schedules

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
    private val sogalService: ISogalService by lazy {
        SogalService().sogalSerivce()
    }

    fun loadSchedulesBy(
        lineWay: String, lineCode: String,
        onSuccess: (response: SogalResponse) -> Unit,
        onError: (errorMessage: String) -> Unit
    ) {
        sogalService.getSogalSchedulesBy(lineWay, lineCode).enqueue(object : Callback<SogalResponse> {
            override fun onFailure(call: Call<SogalResponse>, t: Throwable) {
                onError(t.message.toString())
            }

            override fun onResponse(call: Call<SogalResponse>, response: Response<SogalResponse>) {
                response.body()?.let { sogalResponse ->
                    onSuccess(sogalResponse)
                }

            }

        })
    }
}