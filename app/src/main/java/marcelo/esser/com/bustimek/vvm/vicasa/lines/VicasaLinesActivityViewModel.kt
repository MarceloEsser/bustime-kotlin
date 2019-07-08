package marcelo.esser.com.bustimek.vvm.vicasa.lines

import marcelo.esser.com.bustimek.dao.DataOnHold
import marcelo.esser.com.bustimek.helper.Constants
import marcelo.esser.com.bustimek.service.vicasaServices.VicasaService
import org.json.JSONObject
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
        onSucces: (succes:List< String>) -> Unit,
        onError: (errorMessage: String) -> Unit
    ) {
        service.postVicasaLines("", Constants.VICASA_LINE_NAME, "T", "17", "7")
            .enqueue(object : Callback<Any> {
                override fun onFailure(call: Call<Any>, t: Throwable) {
                    t.message?.let { onError(it) }
                }

                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    var listString: ArrayList<String> = ArrayList()

                    val matchResults: Sequence<MatchResult> = Regex(">(.*?)</a>").findAll(response.toString())

                    matchResults.groupBy {
                        for (string in it.groups) {
                            listString.add(string.toString())
                        }
                    }

                    print(listString)
                }

            })
    }


    fun saveData(lineCode: String, lineName: String, lineWay: String) {
        DataOnHold.setLineCode(lineCode)
        DataOnHold.setLineName(lineName)
        DataOnHold.setLineWay(lineWay)

    }
}