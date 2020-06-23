package esser.marcelo.busoclock.model.schedules

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Saturday(
    @PrimaryKey(autoGenerate = true)
    val saturdayId: Long): BaseSchedule()