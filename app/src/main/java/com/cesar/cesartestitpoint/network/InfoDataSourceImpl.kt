package com.cesar.cesartestitpoint.network

import com.cesar.cesartestitpoint.domain.Resource
import com.cesar.cesartestitpoint.domain.entity.MyDataResponse
import com.cesar.cesartestitpoint.domain.repository.InfoDataSource


class InfoDataSourceImpl(private val retrofitProvider: RetrofitProvider) : InfoDataSource {

    override suspend fun getInfo(): Resource<MyDataResponse> {
        return retrofitProvider.getData()
    }

    override suspend fun getInfoByCurrency(currency:String): Resource<MyDataResponse> {
        return retrofitProvider.getDataByCurrency(currency)
    }
}