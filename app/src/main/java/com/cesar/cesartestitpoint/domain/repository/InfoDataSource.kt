package com.cesar.cesartestitpoint.domain.repository

import com.cesar.cesartestitpoint.domain.Resource
import com.cesar.cesartestitpoint.domain.entity.MyDataResponse


interface InfoDataSource {

    suspend fun getInfo(): Resource<MyDataResponse>
    suspend fun getInfoByCurrency(currency:String): Resource<MyDataResponse>
}