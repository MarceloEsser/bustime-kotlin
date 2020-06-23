package esser.marcelo.busoclock.sla

import esser.marcelo.busoclock.model.LineWay
import esser.marcelo.busoclock.model.schedules.BaseSchedule

object LineDAO {

    var lineName: String = ""
    var lineCode: String = ""
    var lineWay: LineWay? = null

    var workingdays: List<BaseSchedule> = ArrayList()
    var saturdays: List<BaseSchedule> = ArrayList()
    var sundays: List<BaseSchedule> = ArrayList()
}