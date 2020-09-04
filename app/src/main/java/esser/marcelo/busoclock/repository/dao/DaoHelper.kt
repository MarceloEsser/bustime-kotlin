package esser.marcelo.busoclock.repository.dao

import android.content.Context
import androidx.room.Room
import esser.marcelo.busoclock.interfaces.SaveLineDelegate
import esser.marcelo.busoclock.model.favorite.FavoriteLine
import esser.marcelo.busoclock.model.favorite.LineWithSchedules
import esser.marcelo.busoclock.model.schedules.BaseSchedule
import esser.marcelo.busoclock.model.schedules.Saturday
import esser.marcelo.busoclock.model.schedules.Sunday
import esser.marcelo.busoclock.model.schedules.Workingday
import esser.marcelo.busoclock.repository.dao.database.AppDatabase
import esser.marcelo.busoclock.viewModel.SogalSchedulesViewModel
import esser.marcelo.busoclock.viewModel.VicasaSchedulesViewModel
import kotlinx.coroutines.flow.Flow

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/08/20
 */

interface DaoHelperDelegate {
    fun getAll(): Flow<List<LineWithSchedules>?>
    suspend fun getLines(lineId: Long): Flow<List<LineWithSchedules>?>
    suspend fun getLines(name: String, code: String, way: String): Flow<List<LineWithSchedules>?>
    fun clearDatabase()
    suspend fun insertLine(line: FavoriteLine)
}

class DaoHelper(
    context: Context,
    private val sogalSchedulesViewModel: SogalSchedulesViewModel,
    private val vicasaSchedulesViewModel: VicasaSchedulesViewModel
) : DaoHelperDelegate, SaveLineDelegate {

    init {
        sogalSchedulesViewModel.saverDelegate = this
        vicasaSchedulesViewModel.saverDelegate = this
    }

    private val database: AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "bustime"
    ).build()

    private val bustimeDao by lazy {
        database.busTimeDao()
    }

    private lateinit var _workingdays: List<Workingday>
    private lateinit var _saturdays: List<Saturday>
    private lateinit var _sundays: List<Sunday>

    private var _lineId: Long? = null

    override fun getAll(): Flow<List<LineWithSchedules>?> {
        return bustimeDao.getAll()
    }

    override suspend fun getLines(lineId: Long): Flow<List<LineWithSchedules>?> {
        return bustimeDao.getLineBy(lineId)
    }

    override suspend fun getLines(
        name: String,
        code: String,
        way: String
    ): Flow<List<LineWithSchedules>?> {
        return bustimeDao.getLineBy(name, code, way)
    }

    override fun clearDatabase() {
        bustimeDao.deleteAllLines()
        bustimeDao.deleteSaturdays()
        bustimeDao.deleteWorkingdays()
        bustimeDao.deleteSundays()
    }

    override suspend fun insertLine(line: FavoriteLine) {
        this._lineId = bustimeDao.insertLine(line)

        loadSchedules(line)
    }

    private fun loadSchedules(line: FavoriteLine) {
        if (line.isSogal)
            sogalSchedulesViewModel.loadSchedules()
        else
            vicasaSchedulesViewModel.loadSchedules()
    }

    private fun insertSchedules() {
        insertWorkingday()
        insertSaturday()
        insertSunday()
    }

    private fun insertWorkingday() {
        val workingdays = mutableListOf<Workingday>()
        fillWorkingdays(workingdays)

        bustimeDao.insertWorkingdays(workingdays)
    }

    private fun fillWorkingdays(
        workingdays: MutableList<Workingday>
    ) {
        if (!_workingdays.isNullOrEmpty()) {
            for (schedule in _workingdays) {
                val workingday = schedule
                workingday.workindayKey = _lineId
                workingdays.add(workingday)

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
        if (!_saturdays.isNullOrEmpty()) {
            for (schedule in _saturdays) {
                val saturday = schedule as Saturday
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
        if (!_sundays.isNullOrEmpty()) {
            for (schedule in _sundays) {
                val sunday = schedule as Sunday
                sunday.sundayKey = _lineId
                sundays.add(sunday)

            }
        }
    }

    override fun canInsertSchedules(isSogal: Boolean) {
        fillMemberSchedules(isSogal)

        insertSchedules()
    }

    private fun fillMemberSchedules(isSogal: Boolean) {
        if (isSogal) {
            fillSogalSchedulesFromViewModel()
        } else {
            fillVicasaSchedulesFromViewModel()
        }

    }

    private fun fillVicasaSchedulesFromViewModel() {
        this._workingdays = vicasaSchedulesViewModel.workingdays.value ?: listOf()
        this._saturdays = vicasaSchedulesViewModel.saturdays.value ?: listOf()
        this._sundays = vicasaSchedulesViewModel.sundays.value ?: listOf()
    }

    private fun fillSogalSchedulesFromViewModel() {
        this._workingdays = sogalSchedulesViewModel.workingdays.value ?: listOf()
        this._saturdays = sogalSchedulesViewModel.saturdays.value ?: listOf()
        this._sundays = sogalSchedulesViewModel.sundays.value ?: listOf()
    }
}