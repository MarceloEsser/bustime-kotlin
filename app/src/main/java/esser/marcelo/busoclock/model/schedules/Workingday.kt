package esser.marcelo.busoclock.model.schedules

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Workingday(
    @PrimaryKey(autoGenerate = true)
    val workingdayId: Long,
    @ColumnInfo(name = "lineId")
    val workindayKey: Long
)