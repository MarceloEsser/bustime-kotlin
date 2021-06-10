package esser.marcelo.busoclock.repository.service.vicasaServices

import esser.marcelo.busoclock.repository.service.wrapper.ApiResult
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/08/20
 */

interface IVicasaAPI {

    @POST("http://www.vicasa.com.br/busca_linhas_submit.asp")
    @FormUrlEncoded
    fun postLinesAsync(
        @Field("destino", encoded = true) destination: String,
        @Field("origem", encoded = true) origin: String,
        @Field("servico") lineService: String
    ): Deferred<ApiResult<ResponseBody>>

    @GET("http://www.vicasa.com.br/_mostra_linhas.asp")
    fun getSchedulesAsync(
        @Query("LineId") lineCode: String
    ): Deferred<ApiResult<ResponseBody>>


}