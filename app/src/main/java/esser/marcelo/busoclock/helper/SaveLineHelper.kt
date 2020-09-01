package esser.marcelo.busoclock.helper

import esser.marcelo.busoclock.dao.DaoHelper
import android.content.Context
import esser.marcelo.busoclock.dao.LineDAO
import esser.marcelo.busoclock.interfaces.SaveLineDelegate
import esser.marcelo.busoclock.model.favorite.FavoriteLine
import esser.marcelo.busoclock.model.favorite.LineWithSchedules
import esser.marcelo.busoclock.model.schedules.Saturday
import esser.marcelo.busoclock.model.schedules.Sunday
import esser.marcelo.busoclock.model.schedules.Workingday
import esser.marcelo.busoclock.viewModel.SogalItinerariesViewModel
import esser.marcelo.busoclock.viewModel.SogalSchedulesActivityViewModel
import esser.marcelo.busoclock.viewModel.VicasaSchedulesActivityViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SaveLineHelper(val saveLineDelegate: SaveLineDelegate, val context: Context) {
//
//    private lateinit var line: LineWithSchedules
//
//    private lateinit var baseLine: FavoriteLine
//
//    private val dao: DaoHelper by lazy {
//        DaoHelper(this.context.applicationContext)
//    }
//
//    private val sogalItinerariesViewModel: SogalItinerariesViewModel by lazy {
//        SogalItinerariesViewModel()
//    }
//
//    private val sogalSchedulesViewModel: SogalSchedulesActivityViewModel by lazy {
//        SogalSchedulesActivityViewModel()
//    }
//
//    private val vicasaSchedulesViewModel: VicasaSchedulesActivityViewModel by lazy {
//        VicasaSchedulesActivityViewModel()
//    }
//
//    fun saveSogalLine(onLineSaved: () -> Unit, onError: () -> Unit) {
//        baseLine = FavoriteLine()
//
//        baseLine.name = LineDAO.lineName
//        baseLine.code = LineDAO.lineCode
//        baseLine.way = LineDAO.lineWay?.description ?: ""
//
//        sogalSchedulesViewModel.loadSchedules(onSuccess = {
//
//            loadItineraries(onLineSaved, onError)
//
//        }, onError = { errorMessage ->
//            saveLineDelegate.onError(errorMessage)
//        })
//    }
//
//    private fun loadItineraries(onLineSaved: () -> Unit, onError: () -> Unit) {
//        sogalItinerariesViewModel.loadItineraries(onSucces = {
//            fillSogalWithSchedulesValues(onLineSaved, onError)
//        })
//    }
//
//    private fun fillSogalWithSchedulesValues(onLineSaved: () -> Unit, onError: () -> Unit) {
//        val workingdays: MutableList<Workingday> = mutableListOf()
//
//        for (workingday in sogalSchedulesViewModel.workingDays!!) {
//            workingdays.add(
//                Workingday(
//                    hour = workingday.hour,
//                    abrev = workingday.abrev,
//                    apd = workingday.apd
//                )
//            )
//        }
//
//        val saturdays: MutableList<Saturday> = mutableListOf()
//        for (saturday in sogalSchedulesViewModel.saturdays!!) {
//            saturdays.add(
//                Saturday(
//                    hour = saturday.hour,
//                    abrev = saturday.abrev,
//                    apd = saturday.apd
//                )
//            )
//        }
//
//        val sundays: MutableList<Sunday> = mutableListOf()
//        for (sunday in sogalSchedulesViewModel.sundays!!) {
//            sundays.add(
//                Sunday(
//                    hour = sunday.hour,
//                    abrev = sunday.abrev,
//                    apd = sunday.apd
//                )
//            )
//        }
//
//        line = LineWithSchedules(
//            baseLine,
//            workingdays,
//            saturdays,
//            sundays
//        )
//
//        GlobalScope.launch {
//            dao.insert(line, onLineInserted = {
//                onLineSaved()
//            })
//        }
//    }
//
//    fun saveVicasaLineFrom(onLineSaved: () -> Unit) {
//        baseLine = FavoriteLine()
//
//        baseLine.isSogal = false
//
//        baseLine.name = LineDAO.lineName
//        baseLine.code = LineDAO.lineCode
//        baseLine.way = LineDAO.lineWay?.description ?: ""
//
//        vicasaSchedulesViewModel.loadSchedules(onSuccess = {
//            fillVicasalWithSchedulesValues(onLineSaved, {})
//        }, onError = {
//
//        })
//    }
//
//    private fun fillVicasalWithSchedulesValues(onLineSaved: () -> Unit, onError: () -> Unit) {
//        val workingdays: MutableList<Workingday> = mutableListOf()
//
//        for (workingday in vicasaSchedulesViewModel.workingdaysList) {
//            workingdays.add(
//                Workingday(
//                    hour = workingday.hour,
//                    abrev = workingday.abrev,
//                    apd = "N"
//                )
//            )
//        }
//
//        val saturdays: MutableList<Saturday> = mutableListOf()
//        for (saturday in vicasaSchedulesViewModel.saturdaysList) {
//            saturdays.add(
//                Saturday(
//                    hour = saturday.hour,
//                    abrev = saturday.abrev,
//                    apd = "N"
//                )
//            )
//        }
//
//        val sundays: MutableList<Sunday> = mutableListOf()
//        for (sunday in vicasaSchedulesViewModel.sundaysList) {
//            sundays.add(
//                Sunday(
//                    hour = sunday.hour,
//                    abrev = sunday.abrev,
//                    apd = "N"
//                )
//            )
//        }
//
//        line = LineWithSchedules(
//            baseLine,
//            workingdays,
//            saturdays,
//            sundays
//        )
//
//        GlobalScope.launch {
//            dao.insert(line, onLineInserted = {
//                onLineSaved()
//            })
//        }
//    }
}