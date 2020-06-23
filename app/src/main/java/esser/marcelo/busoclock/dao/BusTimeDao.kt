package com.example.kotlin_unit_tests_examples.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.kotlin_unit_tests_examples.models.ViaCep

@Dao
internal interface BusTimeDao {
    @Query("SELECT * FROM viacep")
    fun getAll(): List<ViaCep>

    @Query("SELECT * FROM viacep WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<ViaCep>

    @Insert
    fun insertAll(vararg users: ViaCep)

    @Delete
    fun delete(user: ViaCep)
}