package marcelo.esser.com.bustimek.dao

import marcelo.esser.com.bustimek.model.sogal.SchedulesDTO

object LinesDAO {

    var lineName: String = ""
    var lineCode: String = ""
    var lineWay: String = ""

    var workingdays: List<SchedulesDTO> = ArrayList()
    var saturdays: List<SchedulesDTO> = ArrayList()
    var sundays: List<SchedulesDTO> = ArrayList()
}