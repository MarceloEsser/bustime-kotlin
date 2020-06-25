package esser.marcelo.busoclock.model.schedules

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Saturday(
    @PrimaryKey(autoGenerate = true)
    val saturdayId: Long,
    @ColumnInfo(name = "saturdayKey")
    val saturdayKey: Long
)