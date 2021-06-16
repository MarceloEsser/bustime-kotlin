package esser.marcelo.busoclock.repository.dao.helper

import android.content.Context
import esser.marcelo.busoclock.interfaces.SaveLineDelegate
import esser.marcelo.busoclock.model.favorite.FavoriteLine
import esser.marcelo.busoclock.model.favorite.LineWithSchedules
import esser.marcelo.busoclock.model.schedules.Saturday
import esser.marcelo.busoclock.model.schedules.Sunday
import esser.marcelo.busoclock.model.schedules.Workingday
import esser.marcelo.busoclock.model.sogal.ItinerariesDTO
import esser.marcelo.busoclock.repository.dao.database.AppDatabase
import esser.marcelo.busoclock.viewModel.SogalItinerariesViewModel
import esser.marcelo.busoclock.viewModel.SogalSchedulesViewModel
import esser.marcelo.busoclock.viewModel.VicasaSchedulesViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/08/20
 */


class DaoHelper(
    private val sogalItinerariesViewModel: SogalItinerariesViewModel,
    var sogalSchedulesViewModel: SogalSchedulesViewModel?,
    var vicasaSchedulesViewModel: VicasaSchedulesViewModel?,
    private val context: Context,
    private val database: AppDatabase,
) : SaveLineDelegate {

    private val bustimeDao by lazy {
        database.busTimeDao()
    }

    lateinit var workingdays: List<Workingday>
    lateinit var saturdays: List<Saturday>
    lateinit var sundays: List<Sunday>
    lateinit var itineraries: List<ItinerariesDTO>

    private var _lineId: Long? = null

    suspend fun insertLine(line: FavoriteLine): Long? {
        this._lineId = bustimeDao.insertLine(line)
        insertSchedules()
        if(line.isSogal) insertItineraries()
        return this._lineId
    }

    fun getLines(
        lineName: String,
        lineCode: String,
        lineWayDescription: String
    ): Flow<List<LineWithSchedules>?> {
        return bustimeDao.getLineBy(lineName, lineCode, lineWayDescription)
    }

    suspend fun deleteLine(
        lineName: String,
        lineCode: String,
        lineWayDescription: String
    ) {
        findAndDeleteLine(lineName, lineCode, lineWayDescription)
    }

    private suspend fun findAndDeleteLine(
        lineName: String,
        lineCode: String,
        lineWayDescription: String
    ) {
        bustimeDao.getLineBy(name = lineName, code = lineCode, way = lineWayDescription).collect {
            if (!it.isNullOrEmpty()) {
                val lineId = it[0].line?.id ?: -1
                removeLineFromDatabaseBy(lineId)
            }
        }

    }

    private fun removeLineFromDatabaseBy(lineId: Long) {
        bustimeDao.deleteLineFrom(lineId)
        bustimeDao.deleteSaturdaysFrom(lineId)
        bustimeDao.deleteWorkingdaysFrom(lineId)
        bustimeDao.deleteSundaysFrom(lineId)
        bustimeDao.deleteItinerariesFrom(lineId)
    }

    private fun insertSchedules() {
        insertWorkingDay()
        insertSaturday()
        insertSunday()
    }

    private fun insertItineraries() {
        sogalItinerariesViewModel.loadItineraries(onItinerariesLoaded = {
            val mItineraries: List<ItinerariesDTO> = sogalItinerariesViewModel.itineraries.value ?: listOf()
            if (!mItineraries.isNullOrEmpty()) {
                for (itinerary in mItineraries) {
                    itinerary.itineraryKey = _lineId
                }

                bustimeDao.insertItineraries(mItineraries)
            }
        })
    }

    private fun insertWorkingDay() {
        val workingdays = mutableListOf<Workingday>()
        fillWorkingDays(workingdays)

        bustimeDao.insertWorkingdays(workingdays)
    }

    private fun fillWorkingDays(
        workingDays: MutableList<Workingday>
    ) {
        if (!this.workingdays.isNullOrEmpty()) {
            for (schedule in this.workingdays) {
                val workingday = schedule
                workingday.workindayKey = _lineId
                workingDays.add(workingday)
            }
        }
    }

    private fun insertSaturday() {
        val saturdays = mutableListOf<Saturday>()
        fillSaturdays(saturdays)

        bustimeDao.insertSaturdays(saturdays)
    }

    private fun fillSaturdays(
        saturdays: MutableList<Saturday>
    ) {
        if (!this.saturdays.isNullOrEmpty()) {
            for (schedule in this.saturdays) {
                val saturday = schedule
                saturday.saturdayKey = _lineId
                saturdays.add(saturday)

            }
        }
    }

    private fun insertSunday() {
        val sundays = mutableListOf<Sunday>()
        fillSundays(sundays)

        bustimeDao.insertSundays(sundays)
    }

    private fun fillSundays(
        sundays: MutableList<Sunday>
    ) {
        if (!this.sundays.isNullOrEmpty()) {
            for (schedule in this.sundays) {
                val sunday = schedule
                sunday.sundayKey = _lineId
                sundays.add(sunday)

            }
        }
    }

    override fun canInsertSchedules(isSogal: Boolean) {
        insertSchedules()
    }
}