package esser.marcelo.busoclock.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import esser.marcelo.busoclock.model.Constants
import esser.marcelo.busoclock.model.LineWay
import esser.marcelo.busoclock.model.favorite.FavoriteLine
import esser.marcelo.busoclock.model.sogal.LinesDTO
import esser.marcelo.busoclock.repository.dao.DaoHelperDelegate
import esser.marcelo.busoclock.repository.dao.LineDAO
import esser.marcelo.busoclock.repository.service.sogalServices.SogalServiceDelegate
import esser.marcelo.busoclock.repository.service.wrapper.resource.Status
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/09/20
 */

class SogalLinesViewModel(
    private val daoHelper: DaoHelperDelegate,
    private val service: SogalServiceDelegate,
    private val dispatcher: CoroutineContext
) : ViewModel() {


    private val _lines = MutableLiveData<List<LinesDTO>>()
    val lines: LiveData<List<LinesDTO>> by lazy {
        viewModelScope.launch(dispatcher) {
            setLines()
        }
        return@lazy _lines
    }

    val mFavoriteLine = MutableLiveData<FavoriteLine>()

    private val _isLineFavorite: MutableLiveData<Boolean> = MutableLiveData()
    val isLineFavorite: LiveData<Boolean>
        get() = _isLineFavorite

    private suspend fun setLines() {
        service.getLines().collect { resource ->
            if (resource.requestStatus == Status.success)
                _lines.postValue(resource.data)
        }
    }

    fun validateLine() {
        viewModelScope.launch(dispatcher) {
            daoHelper.getLines(
                LineDAO.lineName,
                LineDAO.lineCode,
                LineDAO.lineWay?.description ?: ""
            ).collect {
                _isLineFavorite.postValue(it?.size == 1)
            }
        }
    }

    fun saveLine() = viewModelScope.launch(dispatcher) {
        val favoriteLine: FavoriteLine = getFavoriteLine()
        daoHelper.insertLine(favoriteLine)
        validateLine()
    }

    fun deleteLine() {
        viewModelScope.launch(dispatcher) {
            daoHelper.deleteLine(
                lineName = LineDAO.lineName,
                lineCode = LineDAO.lineCode,
                lineWayDescription = LineDAO.lineWay?.description ?: ""
            )
            validateLine()
        }
    }

    fun getWaysList(): ArrayList<LineWay> {
        val waysList: ArrayList<LineWay> = ArrayList()
        waysList.add(LineWay("Selecione um sentido", "none"))
        waysList.add(LineWay("Centro Bairro - CB", Constants.CB_WAY))
        waysList.add(LineWay("Bairro Centro - BC", Constants.BC_WAY))

        return waysList
    }

    fun saveData(lineCode: String, lineName: String) {
        LineDAO.lineName = lineName
        LineDAO.lineCode = lineCode
    }

    private fun getFavoriteLine(): FavoriteLine {
        return FavoriteLine(
            isSogal = true,
            name = LineDAO.lineName,
            code = LineDAO.lineCode,
            way = LineDAO.lineWay?.description ?: ""
        )
    }


    fun filterBy(text: String) {
//        val filter: List<LinesDTO>? = mLines.filter {
//            it.name.toLowerCase(Locale.getDefault()).contains(text)
//                    || it.code.toLowerCase(Locale.getDefault()).contains(text)
//        }
//        lines.value = filter ?: listOf()
    }

//    fun findLine() {
//        viewModelScope.launch(dispatcher) {
//            val lines: LineWithSchedules =
//                daoHelper.getLines(LineDAO.lineName, LineDAO.lineCode, LineDAO.lineWay?.way ?: "")
//
//            isFavorite.postValue(lines != null)
//        }
//    }
}
