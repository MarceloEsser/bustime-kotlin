package esser.marcelo.busoclock.service.vicasaServices

import esser.marcelo.busoclock.helper.Constants.BaseUrls.VICASA_BASE_URL
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface IVicasaService {

    @POST("${VICASA_BASE_URL}busca_linhas_submit.asp")
    @FormUrlEncoded
    fun postLoadVicasaLinesBy(
        @Field("destino", encoded = true) destination: String,
        @Field("origem", encoded = true) origin: String,
        @Field("servico") lineService: String
    ): Call<ResponseBody>

    @GET("${VICASA_BASE_URL}_mostra_linhas.asp")
    fun getVicasaSchedules(
        @Query("LineId") lineCode: String
    ): Call<ResponseBody>


}