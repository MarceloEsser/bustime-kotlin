package esser.marcelo.busoclock.viewModel.vicasa

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import esser.marcelo.busoclock.repository.LineHolder
import esser.marcelo.busoclock.model.Constants
import esser.marcelo.busoclock.model.Constants.CB_WAY
import esser.marcelo.busoclock.model.LineWay
import esser.marcelo.busoclock.model.favorite.FavoriteLine
import esser.marcelo.busoclock.model.vicasa.Vicasa
import esser.marcelo.busoclock.repository.dao.database.BusTimeDao
import esser.marcelo.busoclock.repository.service.vicasaServices.VicasaServiceDelegate
import esser.marcelo.busoclock.repository.service.wrapper.resource.Status
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.jsoup.nodes.Entities.escape
import kotlin.coroutines.CoroutineContext

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/09/20
 */

class VicasaLinesViewModel(
    private val service: VicasaServiceDelegate,
    private val dao: BusTimeDao,
    private val dispatcher: CoroutineContext,
) : ViewModel() {

    private val _lines = MutableLiveData<List<Vicasa>>()
    
    val lines: LiveData<List<Vicasa>>
        get() = _lines

    var countryOrigin: String = ""
    var countryDestination: String = ""
    var serviceType: String = ""
    var lineWay: String = CB_WAY

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun loadLines() {
        viewModelScope.launch(dispatcher) {
            service.getLinesFrom(escape(countryDestination), escape(countryOrigin), serviceType)
                .collect { resource ->
                    when (resource.requestStatus) {
                        Status.success -> {
                            if (resource.requestStatus == Status.success && resource.data != null)
                                findVicasaObjects(resource.data)
                        }
                        Status.error -> _error.postValue(resource.message)
                    }
                }
        }
    }

    private val _isLineFavorite: MutableLiveData<Boolean> = MutableLiveData()
    val isLineFavorite: LiveData<Boolean>
        get() = _isLineFavorite

    private fun findVicasaObjects(response: ResponseBody) {

        //Regex to find the vicasa line object on html body
        val lineObjectRegex = "asp\\?(.*?)</a>"

        //getting all the 'parts' what matches with the regex
        val matchResults: Sequence<MatchResult> =
            Regex(lineObjectRegex, RegexOption.MULTILINE).findAll(response.string())

        groupMatchResults(matchResults)
    }

    private fun groupMatchResults(matchResults: Sequence<MatchResult>) {
        val resultsList: ArrayList<Vicasa> = ArrayList()

        //Creating a 'Vicasa' object from html body using the match result
        matchResults.groupBy {
            val lineDescription: String = formatVicasaLineDescription(it)
            val lineCode: String = formatVicasaLineCode(it)

            resultsList.add(Vicasa(lineDescription, lineCode))
        }

        _lines.postValue(resultsList)
    }

    //Getting just the line id from html body
    fun formatVicasaLineCode(it: MatchResult): String {
        //regex to find the line id on html body
        val lineIdRegex = """((.*?)LineId=)"""
        //regex to remove the 'trash' from id on html body
        val lineIdTrashRegex = """',.*"""

        var vicasaLineId = it.value.replace(Regex(lineIdRegex), "")
        vicasaLineId = vicasaLineId.replace(Regex(lineIdTrashRegex), "")

        return vicasaLineId
    }

    //Getting just the line description from html body
    private fun formatVicasaLineDescription(it: MatchResult): String {
        //regex to find the line description on html body
        val lineDescriptionRegex = """(.*?)">"""

        //regex to remove the 'trash' from description on html body
        val lineDescriptionTrashRegex = "</a>"

        var vicasaLineDescription = it.value.replace(Regex(lineDescriptionRegex), "")
        vicasaLineDescription = vicasaLineDescription.replace(lineDescriptionTrashRegex, "")

        return vicasaLineDescription
    }

    fun saveLineData(lineCode: String, lineName: String) {
        LineHolder.lineName = lineName
        LineHolder.lineCode = lineCode
    }

    fun saveFilterData(countryOrigin: String, countryDestination: String, serviceType: String) {
        this.countryOrigin = countryOrigin
        this.countryDestination = countryDestination
        this.serviceType = serviceType
    }

    fun saveLine() = viewModelScope.launch(dispatcher) {
        val favoriteLine: FavoriteLine = getFavoriteLine()
        dao.insertLine(favoriteLine)
    }

    fun validateLine() {
        viewModelScope.launch(dispatcher) {
            dao.getLineBy(
                LineHolder.lineName,
                LineHolder.lineCode,
                LineHolder.lineWay?.description ?: ""
            ).collect {
                _isLineFavorite.postValue(it.size == 1)
            }
            validateLine()
        }
    }

    fun deleteLine() {
        viewModelScope.launch(dispatcher) {
            findAndDeleteLine(
                lineName = LineHolder.lineName,
                lineCode = LineHolder.lineCode,
                lineWayDescription = LineHolder.lineWay?.description ?: ""
            )
            validateLine()
        }
    }


    private suspend fun findAndDeleteLine(
        lineName: String,
        lineCode: String,
        lineWayDescription: String
    ) {
        dao.getLineBy(name = lineName, code = lineCode, way = lineWayDescription).collect {
            if (!it.isNullOrEmpty()) {
                val lineId = it[0].line?.id ?: -1
                removeLineFromDatabaseBy(lineId)
            }
        }

    }

    private fun removeLineFromDatabaseBy(lineId: Long) {
        dao.deleteLineFrom(lineId)
        dao.deleteSaturdaysFrom(lineId)
        dao.deleteWorkingdaysFrom(lineId)
        dao.deleteSundaysFrom(lineId)
    }

    private fun getFavoriteLine(): FavoriteLine {
        return FavoriteLine(
            isSogal = false,
            name = LineHolder.lineName,
            code = LineHolder.lineCode,
            way = LineHolder.lineWay?.description ?: ""
        )
    }

    fun getWaysList(): ArrayList<LineWay> {
        val waysList: ArrayList<LineWay> = ArrayList()
        waysList.add(LineWay("Centro Bairro - CB", CB_WAY))
        waysList.add(LineWay("Bairro Centro - BC", Constants.BC_WAY))
        waysList.add(LineWay("Centro Circular - CC", Constants.CC_WAY))
        waysList.add(LineWay("Bairro Circular - BB", Constants.BB_WAY))

        return waysList
    }

    fun getCountryList(): List<Vicasa> {
        val countryList = ArrayList<Vicasa>()
        countryList.add(Vicasa("Cachoeirinha", Constants.CACHOEIRINHA))
        countryList.add(Vicasa("Canoas", Constants.CANOAS))
        countryList.add(Vicasa("Gravataí", Constants.GRAVATAI))
        countryList.add(Vicasa("Porto Alegre", Constants.POA))

        return countryList
    }

    fun getServiceTypeList(): List<Vicasa> {
        val serviceTypeList = ArrayList<Vicasa>()
        serviceTypeList.add(Vicasa("Todos", Constants.TODOS))
        serviceTypeList.add(Vicasa("Comum", Constants.COMUM))
        serviceTypeList.add(Vicasa("Executiva", Constants.EXECUTIVO))
        serviceTypeList.add(Vicasa("Integração", Constants.INTEGRACAO))
        serviceTypeList.add(Vicasa("Metropolitana", Constants.METROPOLITANA))
        serviceTypeList.add(Vicasa("Rotas", Constants.ROTAS))
        serviceTypeList.add(Vicasa("Seletiva", Constants.SELETIVA))
        serviceTypeList.add(Vicasa("Urbana", Constants.URBANA))

        return serviceTypeList
    }
}