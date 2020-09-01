package esser.marcelo.busoclock.service.sogalServices

import esser.marcelo.busoclock.model.sogal.LinesDTO
import esser.marcelo.busoclock.model.sogal.SogalResponse
import esser.marcelo.busoclock.service.wrapper.ApiResult
import kotlinx.coroutines.Deferred
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/08/20
 */

interface ISogalAPI {
    @POST("http://sogal.com.br/wp-content/themes/MobidickTheme/linhas/searchLine.php")
    @FormUrlEncoded
    fun getSogalSchedulesBy(
        @Field("action") lineWay: String,
        @Field("linha") lineCode: String
    ): Deferred<ApiResult<SogalResponse>>

    @POST("http://sogal.com.br/wp-content/themes/MobidickTheme/linhas/searchLine.php")
    @FormUrlEncoded
    fun getSogalList(
        @Field("action") action: String
    ): Deferred<ApiResult<List<LinesDTO>>>

    @POST("http://sogal.com.br/wp-content/themes/MobidickTheme/linhas/searchLine.php")
    @FormUrlEncoded
    fun getSogalItineraries(
        @Field("action") action: String,
        @Field("linha") linha: String
    ): Deferred<ApiResult<LinesDTO>>
}