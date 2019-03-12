package marcelo.esser.com.bustimek.dao

/**
 * @author Marcelo Esser
 * @since 12/03/19
 */
class DataOnHold {

    val Companion.lineCode: String?
        get() = _lineCode

    val Companion.lineName: String?
        get() = _lineName

    val Companion.lineWay: String?
        get() = _lineWay

    companion object {
        private var _lineCode: String? = null
        private var _lineName: String? = null
        private var _lineWay: String? = null
    }

    fun Companion.setLineCode(lineCode: String) {
        _lineCode = lineCode
    }

    fun Companion.setLineName(lineName: String) {
        _lineName = lineName
    }

    fun Companion.setLineWay(lineWay: String) {
        _lineWay = lineWay
    }
}