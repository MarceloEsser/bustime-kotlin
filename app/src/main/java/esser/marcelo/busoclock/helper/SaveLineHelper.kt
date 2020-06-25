package esser.marcelo.busoclock.helper

import DaoHelper
import android.content.Context
import esser.marcelo.busoclock.dao.LineDAO
import esser.marcelo.busoclock.interfaces.SaveLindeDelegate
import esser.marcelo.busoclock.model.BaseLine
import esser.marcelo.busoclock.model.favorite.FavoriteItineraries
import esser.marcelo.busoclock.model.favorite.FavoriteLine
import esser.marcelo.busoclock.model.favorite.SogalLineWithSchedules
import esser.marcelo.busoclock.model.schedules.Saturday
import esser.marcelo.busoclock.model.schedules.Sunday
import esser.marcelo.busoclock.model.schedules.Workingday
import esser.marcelo.busoclock.vvm.sogal.itineraries.SogalItinerariesActivityViewModel
import esser.marcelo.busoclock.vvm.sogal.schedules.SogalSchedulesActivityViewModel
import esser.marcelo.busoclock.vvm.vicasa.lines.VicasaLinesActivityViewModel
import esser.marcelo.busoclock.vvm.vicasa.schedules.VicasaSchedulesActivityViewModel

class SaveLineHelper(val saveLindeDelegate: SaveLindeDelegate, val context: Context) {

    private lateinit var line: SogalLineWithSchedules

    private lateinit var baseLine: FavoriteLine

    private val sogalItinerariesViewModel: SogalItinerariesActivityViewModel by lazy {
        SogalItinerariesActivityViewModel()
    }

    private val sogalSchedulesViewModel: SogalSchedulesActivityViewModel by lazy {
        SogalSchedulesActivityViewModel()
    }

    private val vicasaLinesViewmodel: VicasaLinesActivityViewModel by lazy {
        VicasaLinesActivityViewModel()
    }

    private val vicasaSchedulesViewModel: VicasaSchedulesActivityViewModel by lazy {
        VicasaSchedulesActivityViewModel()
    }

    fun saveSogalLineFrom() {
        baseLine = FavoriteLine()
        baseLine.name = LineDAO.lineName
        baseLine.code = LineDAO.lineCode
        sogalSchedulesViewModel.loadSchedules(onSuccess = {

            loadItineraries()

        }, onError = { errorMessage ->
            saveLindeDelegate.onError(errorMessage)
        })
    }

    private fun loadItineraries() {
        sogalItinerariesViewModel.loadItineraries(onSucces = {
            fillSogalWithSchedulesValues()
        }, onError = { errorMessage ->

        })
    }

    private fun fillSogalWithSchedulesValues() {
        val workingdays = sogalSchedulesViewModel.workingDays as List<Workingday>
        val saturdays = sogalSchedulesViewModel.saturdays as List<Saturday>
        val sunday = sogalSchedulesViewModel.sundays as List<Sunday>

        line = SogalLineWithSchedules(
            baseLine,
            workingdays,
            saturdays,
            sunday,
            sogalItinerariesViewModel.itineraries
        )
        val dao  = DaoHelper(context = this.context)
        dao.insert(line)
    }

    fun saveVicasaLineFrom(lineCode: String) {

    }

}