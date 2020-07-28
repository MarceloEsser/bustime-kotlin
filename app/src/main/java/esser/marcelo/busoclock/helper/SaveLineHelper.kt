package esser.marcelo.busoclock.helper

import esser.marcelo.busoclock.dao.DaoHelper
import android.content.Context
import esser.marcelo.busoclock.dao.LineDAO
import esser.marcelo.busoclock.interfaces.SaveLineDelegate
import esser.marcelo.busoclock.model.favorite.FavoriteLine
import esser.marcelo.busoclock.model.favorite.SogalLineWithSchedules
import esser.marcelo.busoclock.model.schedules.Saturday
import esser.marcelo.busoclock.model.schedules.Sunday
import esser.marcelo.busoclock.model.schedules.Workingday
import esser.marcelo.busoclock.vvm.sogal.itineraries.SogalItinerariesActivityViewModel
import esser.marcelo.busoclock.vvm.sogal.schedules.SogalSchedulesActivityViewModel
import esser.marcelo.busoclock.vvm.vicasa.lines.VicasaLinesActivityViewModel
import esser.marcelo.busoclock.vvm.vicasa.schedules.VicasaSchedulesActivityViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SaveLineHelper(val saveLineDelegate: SaveLineDelegate, val context: Context) {

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

    fun saveSogalLine(onLineSaved: () -> Unit, onError: () -> Unit) {
        baseLine = FavoriteLine()

        baseLine.name = LineDAO.lineName
        baseLine.code = LineDAO.lineCode

        sogalSchedulesViewModel.loadSchedules(onSuccess = {

            loadItineraries(onLineSaved, onError)

        }, onError = { errorMessage ->
            saveLineDelegate.onError(errorMessage)
        })
    }

    private fun loadItineraries(onLineSaved: () -> Unit, onError: () -> Unit) {
        sogalItinerariesViewModel.loadItineraries(onSucces = {
            fillSogalWithSchedulesValues(onLineSaved, onError)
        }, onError = { errorMessage ->

        })
    }

    private fun fillSogalWithSchedulesValues(onLineSaved: () -> Unit, onError: () -> Unit) {
        val workingdays: MutableList<Workingday> = mutableListOf()
            for (workingday in sogalSchedulesViewModel.workingDays!!) {
                workingdays.add(Workingday(hour = workingday.hour, abrev = workingday.abrev, apd = workingday.apd))
            }
        val saturdays: MutableList<Saturday> = mutableListOf()
            for (workingday in sogalSchedulesViewModel.saturdays!!) {
                saturdays.add(Saturday(hour = workingday.hour, abrev = workingday.abrev, apd = workingday.apd))
            }

        val sundays: MutableList<Sunday> = mutableListOf()
            for (workingday in sogalSchedulesViewModel.saturdays!!) {
                sundays.add(Sunday(hour = workingday.hour, abrev = workingday.abrev, apd = workingday.apd))
            }

        line = SogalLineWithSchedules(
            baseLine,
            workingdays,
            saturdays,
            sundays
        )
        val dao: DaoHelper = DaoHelper(this.context.applicationContext)
        GlobalScope.launch {
            dao.insert(line, onLineInserted = {
                onLineSaved()
            })
        }
    }

    fun saveVicasaLineFrom(lineCode: String) {

    }

}