package marcelo.esser.com.bustimek.dao

import marcelo.esser.com.bustimek.model.sogal.SchedulesDTO

/**
 * @author Marcelo Esser
 * @since 12/03/19
 */
class DataOnHold {

    val lineCode: String?
        get() = _lineCode

    val lineName: String?
        get() = _lineName

    val lineWay: String?
        get() = _lineWay

    val workingdays: List<SchedulesDTO>?
        get () = _workingdays

    val saturdays: List<SchedulesDTO>?
        get() = _saturdays

    val sundays: List<SchedulesDTO>?
        get() = _sundays


    companion object {
        private var _lineCode: String? = null
        private var _lineName: String? = null
        private var _lineWay: String? = null

        fun setLineCode(lineCode: String) {
            DataOnHold()._setLineCode(lineCode)
        }

        fun setLineName(lineName: String) {
            DataOnHold()._setLineName(lineName)
        }

        fun setLineWay(lineWay: String) {
            DataOnHold()._setLineWay(lineWay)
        }

        private var _workingdays: List<SchedulesDTO>? = null
        private var _saturdays: List<SchedulesDTO>? = null
        private var _sundays: List<SchedulesDTO>? = null
    }

    private fun _setWorkingDays(workingdays: List<SchedulesDTO>?) {
        _workingdays = workingdays
    }

    private fun _setSaturdays(workingdays: List<SchedulesDTO>?) {
        _saturdays = saturdays
    }

    private fun _setSundays(sundays: List<SchedulesDTO>?) {
        _sundays = sundays
    }

    private fun _setLineCode(lineCode: String) {
        _lineCode = lineCode
    }

    private fun _setLineName(lineName: String) {
        _lineName = lineName
    }

    private fun _setLineWay(lineWay: String) {
        _lineWay = lineWay
    }
}