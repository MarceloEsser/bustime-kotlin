package marcelo.esser.com.bustimek.service.vicasaServices

import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface IVicasaService {

    @POST("busca_linhas_submit.asp")
    @FormUrlEncoded
    fun postVicasaLines(
        @Field("destino") destination: String,
        @Field("origem") origin: String,
        @Field("linha") line: String,
        @Field("tipo") type: String,
        @Field("servicolinha") lineService: String
    ): Call<ResponseBody>

    @POST("busca_linhas_submit.asp")
    @FormUrlEncoded
    fun postLoadVicasaLinesBy(
        @Field("destino") destination: String,
        @Field("origem") origin: String,
        @Field("servico") lineService: String
        ): Call<ResponseBody>

    @GET("_mostra_linhas.asp")
    fun getVicasaSchedules(
        @Query("LineId") lineCode: String
        ): Call<ResponseBody>


}