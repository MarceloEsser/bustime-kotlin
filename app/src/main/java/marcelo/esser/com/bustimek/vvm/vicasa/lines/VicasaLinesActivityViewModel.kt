package marcelo.esser.com.bustimek.vvm.vicasa.lines

import marcelo.esser.com.bustimek.helper.BaseUrls
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

    fun loadVicasaLines(
        lineOrigin: String = "",
        lineDestination: String = "",
        linService: String = "",
        onSucces: (succes: List<String>) -> Unit,
        onError: (errorMessage: String) -> Unit
    ) {
        service.postVicasaLines(lineDestination,lineOrigin,"","cidadeLinha",linService,"","")
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    t.message?.let { onError(it) }
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    val resultsList: ArrayList<String> = ArrayList()

                    val matchResults: Sequence<MatchResult> =
                        Regex(">(.*?)</a>", RegexOption.MULTILINE).findAll(response.body()?.string() ?: "")

                    matchResults.groupBy {
                        if (!resultsList.contains(it.value))
                            resultsList.add(
                                it.value
                                    .replace(">", "")
                                    .replace(">", "")
                                    .replace("a", "")
                                    .replace("/", " ")
                                    .replace("<", "")
                            )
                    }

                    onSucces(resultsList)
                }

            })
    }
}