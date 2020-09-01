package esser.marcelo.busoclock.viewModel

import androidx.lifecycle.ViewModel
import esser.marcelo.busoclock.dao.DaoHelper
import esser.marcelo.busoclock.dao.LineDAO
import esser.marcelo.busoclock.helper.Constants
import esser.marcelo.busoclock.helper.Constants.CB_WAY
import esser.marcelo.busoclock.model.LineWay
import esser.marcelo.busoclock.model.vicasa.Vicasa
import esser.marcelo.busoclock.service.vicasaServices.VicasaService
import esser.marcelo.busoclock.service.vicasaServices.VicasaServiceDelegate
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.ResponseBody
import org.jsoup.nodes.Entities.escape
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

/**
 * @author Marcelo Esser
 * @since 19/02/19
 */
class VicasaLinesActivityViewModel(
    private val service: VicasaServiceDelegate,
    private val daoHelper: DaoHelper,
    private val dispatcher: CoroutineContext,
) : ViewModel() {
    //private val service = VicasaService().vicasaService()
    var resultsList: ArrayList<Vicasa> = ArrayList()

    var countryOrigin: String = ""
    var countryDestination: String = ""
    var serviceType: String = ""
    var lineWay: String = CB_WAY

    fun loadVicasaLinesBy(
        onSuccess: (succes: List<Vicasa>) -> Unit,
        onError: (errorMessage: String) -> Unit
    ) {
//        service.postLoadVicasaLinesBy(escape(countryDestination), escape(countryOrigin), serviceType)
//            .enqueue(object : Callback<ResponseBody> {
//                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                    t.message?.let { onError(it) }
//                }
//
//                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//
//                    findVicasaObjects(response)
//
//                    onSuccess(resultsList)
//                }
//
//            })
    }

    fun getWaysList(): ArrayList<LineWay> {
        val waysList: ArrayList<LineWay> = ArrayList()
        waysList.add(LineWay("Selecione um sentido", "none"))
        waysList.add(LineWay("Centro Bairro - CB", CB_WAY))
        waysList.add(LineWay("Bairro Centro - BC", Constants.BC_WAY))
        waysList.add(LineWay("Centro Circular - CC", Constants.CC_WAY))
        waysList.add(LineWay("Bairro Circular - BB", Constants.BB_WAY))

        return waysList
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

    fun saveLineData(lineCode: String, lineName: String) {
        LineDAO.lineName = lineName
        LineDAO.lineCode = lineCode
    }

    fun saveFilterData(countryOrigin: String, countryDestination: String, serviceType: String) {
        this.countryOrigin = countryOrigin
        this.countryDestination = countryDestination
        this.serviceType = serviceType
    }
}