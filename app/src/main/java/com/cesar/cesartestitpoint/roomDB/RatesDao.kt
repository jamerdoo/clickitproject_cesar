package com.cesar.cesartestitpoint.roomDB

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cesar.cesartestitpoint.domain.entity.local.LocalRates

@Dao
interface RatesDao {

    @Insert
    suspend fun insert(localRates: LocalRates): Long

    @Update
    suspend  fun update(localRates: LocalRates): Int

    @Delete
    suspend fun delete(localRates: LocalRates): Int

    @Query("SELECT * FROM " + LocalRates.TABLE_NAME+ " where Base = :base Limit 1")
    suspend fun getDataById(base:String): LocalRates?

    @Query("SELECT * FROM " + LocalRates.TABLE_NAME)
    suspend fun getAllData(): List<LocalRates>?

}