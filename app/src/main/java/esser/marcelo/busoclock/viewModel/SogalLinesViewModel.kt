package esser.marcelo.busoclock.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import esser.marcelo.busoclock.model.Constants
import esser.marcelo.busoclock.model.LineWay
import esser.marcelo.busoclock.model.favorite.FavoriteLine
import esser.marcelo.busoclock.model.sogal.LinesDTO
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
    private val service: SogalServiceDelegate,
    private val dispatcher: CoroutineContext
) : ViewModel() {


    private val _allLines = MutableLiveData<List<LinesDTO>>()
    private val _lines = MutableLiveData<List<LinesDTO>>()
    val lines: LiveData<List<LinesDTO>> by lazy {
        loadLines()
        return@lazy _lines
    }

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    val mFavoriteLine = MutableLiveData<FavoriteLine>()

    private val _isLineFavorite: MutableLiveData<Boolean> = MutableLiveData()
    val isLineFavorite: LiveData<Boolean>
        get() = _isLineFavorite

    fun loadLines() {
        viewModelScope.launch(dispatcher) {
            service.getLines().collect { resource ->
                when (resource.requestStatus) {
                    Status.success -> {
                        _lines.postValue(resource.data)
                        _allLines.postValue(resource.data)
                    }
                    Status.error -> _error.postValue(resource.message)
                }
            }
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

    fun filterBy(text: String) {
        val linesToFilter = _allLines.value
        val filter: List<LinesDTO>? = linesToFilter?.filter {
            it.name.toLowerCase(Locale.getDefault()).contains(text)
                    || it.code.toLowerCase(Locale.getDefault()).contains(text)
        }
        _lines.value = filter ?: listOf()
    }

}
