package esser.marcelo.busoclock.model.schedules

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Sunday (
    @PrimaryKey(autoGenerate = true)
    val sundayId: Long,
    @ColumnInfo(name = "lineId")
    val sundayKey: Long
)