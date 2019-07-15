package marcelo.esser.com.bustimek.vvm.vicasa.lines

import marcelo.esser.com.bustimek.model.vicasa.Vicasa
import marcelo.esser.com.bustimek.service.vicasaServices.VicasaService
import okhttp3.ResponseBody
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


    fun loadVicasaLinesBy(
        lineOrigin: String = "",
        lineDestination: String = "",
        lineService: String = "",
        onSuccess: (succes: List<Vicasa>) -> Unit,
        onError: (errorMessage: String) -> Unit
    ) {
        service.postLoadVicasaLinesBy(lineDestination, lineOrigin, lineService)
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
            val vicasaLineId: String = formatVicasaLineId(it)

            resultsList.add(Vicasa(vicasaLineDescription, vicasaLineId))

        }
    }

    fun formatVicasaLineId(it: MatchResult): String {
        var vicasaLineId = it.value.replace(Regex("""((.*?)LineId=)"""), "")
        vicasaLineId = vicasaLineId.replace(Regex("""',.*"""), "")
        return vicasaLineId
    }

    private fun formatVicasaLineDescription(it: MatchResult): String {
        var vicasaLineDescription = it.value.replace(Regex("""(.*?)">"""), "")
        vicasaLineDescription = vicasaLineDescription.replace("</a>", "")
        return vicasaLineDescription
    }
}