package marcelo.esser.com.bustimek.service.vicasaServices

import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface IVicasaService {

    @POST("busca_linhas_submit.asp")
    @FormUrlEncoded
    fun postVicasaLines(
        @Field("linha") line: String,
        @Field("tipo") type: String,
        @Field("servicolinha") lineService: String,
        @Field("x") xCoordinate: String,
        @Field("y") yCoordinate: String
    ): Call<Any>

}