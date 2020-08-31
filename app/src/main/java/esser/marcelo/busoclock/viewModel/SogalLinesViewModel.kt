package esser.marcelo.busoclock.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import esser.marcelo.busoclock.dao.DaoHelper
import esser.marcelo.busoclock.dao.LineDAO
import esser.marcelo.busoclock.helper.Constants
import esser.marcelo.busoclock.model.LineWay
import esser.marcelo.busoclock.model.favorite.LineWithSchedules
import esser.marcelo.busoclock.model.sogal.LinesDTO
import esser.marcelo.busoclock.service.sogalServices.SogalService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * @author Marcelo Esser
 * @since 19/02/19
 */
class SogalLinesViewModel(
    private val daoHelper: DaoHelper
) : ViewModel() {
    private val service = SogalService().sogalSerivce()
    private val SEARCH_LINES: String = "buscaLinhas"

    private lateinit var mLines: List<LinesDTO>
    val lines: MutableLiveData<List<LinesDTO>> = MutableLiveData()

    val isFavorite: MutableLiveData<Boolean> = MutableLiveData()

    /**
     * Function used to load the itineraries list
     * the HOFs referenced here is to help the SaveLineHelper
     * {@link import esser.marcelo.busoclock.helper.SaveLineHelper#saveSogalLine({}, {}) saveSogalLine}.
     */
    fun loadSogalLines(
        onSucces: (linesDTO: List<LinesDTO>) -> Unit = {},
        onError: (errorMessage: String) -> Unit = {}
    ) {
        service.getSogalList(action = SEARCH_LINES).enqueue(object : Callback<List<LinesDTO>> {
            override fun onFailure(call: Call<List<LinesDTO>>, t: Throwable) {
                onError(t.message!!)
            }

            override fun onResponse(
                call: Call<List<LinesDTO>>,
                response: Response<List<LinesDTO>>
            ) {
                if (response.body() != null) {
                    lines.value = response.body()
                    mLines = response.body() ?: listOf()
                    onSucces(response.body()!!)
                }
            }

        })
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
        val filter: List<LinesDTO>? = mLines.filter {
            it.name.toLowerCase(Locale.getDefault()).contains(text)
                    || it.code.toLowerCase(Locale.getDefault()).contains(text)
        }
        lines.value = filter ?: listOf()
    }

    fun findLine() {
        GlobalScope.launch {
            val lines: List<LineWithSchedules> =
                daoHelper.findLineBy(LineDAO.lineName, LineDAO.lineCode, LineDAO.lineWay?.way ?: "")

            isFavorite.postValue(lines.size == 1)
        }
    }
}