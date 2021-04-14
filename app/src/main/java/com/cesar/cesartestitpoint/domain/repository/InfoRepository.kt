package com.cesar.cesartestitpoint.domain.repository

import com.cesar.cesartestitpoint.domain.Resource
import com.cesar.cesartestitpoint.domain.entity.MyDataResponse

class InfoRepository(private val infoDataSource: InfoDataSource) {

    suspend fun getInfo() : Resource<MyDataResponse> = infoDataSource.getInfo()
    suspend fun getInfoByCurrency(currency:String) : Resource<MyDataResponse> = infoDataSource.getInfoByCurrency(currency)
}