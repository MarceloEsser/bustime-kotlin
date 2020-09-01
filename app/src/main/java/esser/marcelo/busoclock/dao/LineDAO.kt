package esser.marcelo.busoclock.dao

import esser.marcelo.busoclock.model.LineWay
import esser.marcelo.busoclock.model.schedules.BaseSchedule

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/08/20
 */

object LineDAO {

    var lineId: Long? = 0
    var lineName: String = ""
    var lineCode: String = ""
    var lineWay: LineWay? = null

    var workingdays: List<BaseSchedule> = ArrayList()
    var saturdays: List<BaseSchedule> = ArrayList()
    var sundays: List<BaseSchedule> = ArrayList()
}