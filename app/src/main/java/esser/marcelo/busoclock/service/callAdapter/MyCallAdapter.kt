package esser.marcelo.busoclock.service.callAdapter

import esser.marcelo.busoclock.service.ApiResult
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class MyCallAdapter<T>(
    private val mResponseType: Type
) : CallAdapter<T, Deferred<ApiResult<T>>> {
    override fun responseType(): Type = mResponseType

    override fun adapt(call: Call<T>): Deferred<ApiResult<T>> {
        val completableDeferred = CompletableDeferred<ApiResult<T>>()

        val callBack = setupCallBackFrom(completableDeferred)

        call.enqueue(callBack)

        return completableDeferred
    }

    private fun setupCallBackFrom(completableDeferred: CompletableDeferred<ApiResult<T>>): Callback<T> {
        return object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                completableDeferred.complete(ApiResult.create(response))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                completableDeferred.complete(ApiResult.create(t))
            }

        }
    }
}