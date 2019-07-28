package esser.marcelo.busoclock.box

import esser.marcelo.busoclock.model.boxModels.BoxItineraries
import esser.marcelo.busoclock.model.boxModels.BoxLine
import esser.marcelo.busoclock.model.boxModels.BoxSchedule

/**
 * @author Wottrich
 * @since 25/07/2019.
 */

object Bus {

    fun getLine (idBoxLine: Long) : BoxLine {
        return box<BoxLine>().get(idBoxLine)
    }

    fun getLine (idBoxLine: Long, boxLine: (BoxLine) -> Unit) {
        val box = box<BoxLine>().get(idBoxLine)
        boxLine(box)
    }

    fun getLines () : List<BoxLine> {
        return boxList()
    }

    fun getSogalLines () : List<BoxLine> {

        val sogalLines: MutableList<BoxLine> = boxList()

        return sogalLines.filter { it.isSogalLine }
    }

    fun getVicasaLines () : List<BoxLine> {

        val vicasaLines: MutableList<BoxLine> = boxList()

        return vicasaLines.filter { !it.isSogalLine }
    }

    fun getWorkingDays(idBoxLine: Long) : List<BoxSchedule> {
        return box<BoxLine>().get(idBoxLine).workingDays
    }

    fun getSaturdaysDays(idBoxLine: Long) : List<BoxSchedule> {
        return box<BoxLine>().get(idBoxLine).saturdays
    }

    fun getSundaysDays(idBoxLine: Long) : List<BoxSchedule> {
        return box<BoxLine>().get(idBoxLine).sundays
    }

    fun getSogalItineraries(idBoxLine: Long) : List<BoxItineraries> {
        return box<BoxLine>().get(idBoxLine).sogalItineraries
    }

    fun putLine(vararg boxLine: BoxLine, success: () -> Unit = {}) {
        put(boxLine)
        success()
    }

}