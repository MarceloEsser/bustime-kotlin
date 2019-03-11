package marcelo.esser.com.bustimek.service.sogalServices

import marcelo.esser.com.bustimek.model.sogal.LinesDTO
import marcelo.esser.com.bustimek.model.sogal.SogalResponse
import retrofit2.Call
import retrofit2.http.*

/**
 * @author Marcelo Esser
 * @since 19/02/19
 */
interface ISogalService {
    @POST("wp-content/themes/MobidickTheme/linhas/searchLine.php")
    @FormUrlEncoded
    fun getSogalSchedulesBy(@Field("action") action: String, @Field("linha") linha: String): Call<SogalResponse>

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