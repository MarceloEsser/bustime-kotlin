package esser.marcelo.busoclock.service.sogalServices

import esser.marcelo.busoclock.model.sogal.LinesDTO
import esser.marcelo.busoclock.model.sogal.SogalResponse
import retrofit2.Call
import retrofit2.http.*

/**
 * @author Marcelo Esser
 * @since 19/02/19
 */
interface ISogalService {
    @POST("wp-content/themes/MobidickTheme/linhas/searchLine.php")
    @FormUrlEncoded
    fun getSogalSchedulesBy(@Field("action") lineWay: String, @Field("linha") lineCode: String): Call<SogalResponse>

    @POST("wp-content/themes/MobidickTheme/linhas/searchLine.php")
    @FormUrlEncoded
    fun getSogalList(@Field("action") action: String): Call<List<LinesDTO>>

    @POST("wp-content/themes/MobidickTheme/linhas/searchLine.php")
    @FormUrlEncoded
    fun getSogalItineraries(@Field("action") action: String, @Field("linha") linha: String): Call<LinesDTO>

    @GET("mapa/")
    fun getBusRoute(
        @Query("linha") lineCode: String,
        @Query("sentido") lineWay: String

    ): Call<Any>
}