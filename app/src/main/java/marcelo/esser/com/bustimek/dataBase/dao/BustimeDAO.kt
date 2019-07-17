package marcelo.esser.com.bustimek.dataBase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import marcelo.esser.com.bustimek.model.BaseLine

@Dao
interface BustimeDAO {

    @Insert
    fun insertLine(baseLine: BaseLine)

    @Delete
    fun deleteLine(lindeCode: String)
}