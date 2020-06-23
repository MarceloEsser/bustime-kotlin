package esser.marcelo.busoclock.model.schedules

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Sunday(
    @PrimaryKey(autoGenerate = true)
    val sundayId: Long): BaseSchedule()