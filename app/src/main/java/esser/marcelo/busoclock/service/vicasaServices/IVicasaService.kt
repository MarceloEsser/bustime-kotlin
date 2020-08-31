package esser.marcelo.busoclock.service.vicasaServices

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface IVicasaService {

    @POST("busca_linhas_submit.asp")
    @FormUrlEncoded
    fun postLoadVicasaLinesBy(
        @Field("destino", encoded = true) destination: String,
        @Field("origem", encoded = true) origin: String,
        @Field("servico") lineService: String
    ): Call<ResponseBody>

    @GET("_mostra_linhas.asp")
    fun getVicasaSchedules(
        @Query("LineId") lineCode: String
    ): Call<ResponseBody>


}