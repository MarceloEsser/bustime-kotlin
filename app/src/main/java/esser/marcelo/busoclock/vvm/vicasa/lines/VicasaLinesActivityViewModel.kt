package esser.marcelo.busoclock.vvm.vicasa.lines

import android.net.Uri.encode
import esser.marcelo.busoclock.dao.LineDAO
import esser.marcelo.busoclock.helper.Constants.CB_WAY
import esser.marcelo.busoclock.model.vicasa.Vicasa
import esser.marcelo.busoclock.service.vicasaServices.VicasaService
import okhttp3.ResponseBody
import org.jsoup.nodes.Entities.escape
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author Marcelo Esser
 * @since 19/02/19
 */
class VicasaLinesActivityViewModel {
    private val service = VicasaService().vicasaService()
    var resultsList: ArrayList<Vicasa> = ArrayList()

    var countryOrigin: String = ""
    var countryDestination: String = ""
    var serviceType: String = ""
    var lineWay: String = CB_WAY

    fun loadVicasaLinesBy(
        onSuccess: (succes: List<Vicasa>) -> Unit,
        onError: (errorMessage: String) -> Unit
    ) {
        service.postLoadVicasaLinesBy(escape(countryDestination), escape(countryOrigin), serviceType)
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    t.message?.let { onError(it) }
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                    findVicasaObjects(response)

                    onSuccess(resultsList)
                }

            })
    }

    private fun findVicasaObjects(
        response: Response<ResponseBody>
    ) {
        val matchResults: Sequence<MatchResult> =
            Regex("asp\\?(.*?)</a>", RegexOption.MULTILINE).findAll(response.body()?.string() ?: "")

        matchResults.groupBy {
            val vicasaLineDescription = formatVicasaLineDescription(it)
            val vicasaLineCode: String = formatVicasaLineCode(it)

            resultsList.add(Vicasa(vicasaLineDescription, vicasaLineCode))

        }
    }

    fun formatVicasaLineCode(it: MatchResult): String {
        var vicasaLineId = it.value.replace(Regex("""((.*?)LineId=)"""), "")
        vicasaLineId = vicasaLineId.replace(Regex("""',.*"""), "")
        return vicasaLineId
    }

    private fun formatVicasaLineDescription(it: MatchResult): String {
        var vicasaLineDescription = it.value.replace(Regex("""(.*?)">"""), "")
        vicasaLineDescription = vicasaLineDescription.replace("</a>", "")
        return vicasaLineDescription
    }

    fun saveLineData(lineCode: String, lineName: String, lineWay: String) {
        LineDAO.lineName = lineName
        LineDAO.lineCode = lineCode
        LineDAO.lineWay = lineWay

    }

    fun saveFilterData(countryOrigin: String, countryDestination: String, serviceType: String) {
        this.countryOrigin = countryOrigin
        this.countryDestination = countryDestination
        this.serviceType = serviceType
    }
}