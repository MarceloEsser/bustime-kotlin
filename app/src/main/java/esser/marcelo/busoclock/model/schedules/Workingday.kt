package esser.marcelo.busoclock.model.schedules

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Workingday(
    @PrimaryKey(autoGenerate = true)
    val workingdayId: Long): BaseSchedule()