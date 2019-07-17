package esser.marcelo.busoclock.dao

import esser.marcelo.busoclock.model.sogal.SchedulesDTO

object LinesDAO {

    var lineName: String = ""
    var lineCode: String = ""
    var lineWay: String = ""

    var workingdays: List<SchedulesDTO> = ArrayList()
    var saturdays: List<SchedulesDTO> = ArrayList()
    var sundays: List<SchedulesDTO> = ArrayList()
}