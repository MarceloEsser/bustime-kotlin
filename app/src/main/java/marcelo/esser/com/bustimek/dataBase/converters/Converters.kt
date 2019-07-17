package marcelo.esser.com.bustimek.dataBase.converters

import androidx.room.TypeConverter
import marcelo.esser.com.bustimek.model.sogal.SchedulesDTO

class Converters() {
    @TypeConverter
    fun fromWorkingdays(workingdays: List<SchedulesDTO>): List<SchedulesDTO> {
        return workingdays
    }

    @TypeConverter
    fun fromSaturdays(saturdays: List<SchedulesDTO>): List<SchedulesDTO> {
        return saturdays
    }

    @TypeConverter
    fun fromSundays(sundays: List<SchedulesDTO>): List<SchedulesDTO> {
        return sundays
    }
}