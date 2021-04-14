package com.cesar.cesartestitpoint.roomDB

import androidx.lifecycle.LiveData
import com.cesar.cesartestitpoint.domain.entity.local.LocalRates

class RatesRepository(private val ratesDao: RatesDao){

//    private val ratesDao: RatesDao? = RatesDatabase.getInstance(application)?.ratesDao()


    suspend fun insert(localRates: LocalRates): Long {
        return ratesDao.insert(localRates)
    }

    suspend  fun update(localRates: LocalRates): Int {
        return ratesDao.update(localRates)
    }

    suspend fun delete(localRates: LocalRates): Int {
        return ratesDao.delete(localRates)
    }

    suspend fun getDataById(base:String): LocalRates? {
        return ratesDao.getDataById(base)
    }

    suspend fun getAnyRecipe(): List<LocalRates>? {
        return ratesDao.getAllData()
    }
}