package com.cesar.cesartestitpoint.network

import com.cesar.cesartestitpoint.domain.Resource
import com.cesar.cesartestitpoint.domain.entity.MyDataResponse
import com.cesar.cesartestitpoint.network.apiservice.DataApi
import com.cesar.cesartestitpoint.utils.Logger
import com.cesar.cesartestitpoint.utils.RestApi
import retrofit2.awaitResponse

class RetrofitProvider : Logger {

    override val nameClass: String get() = "--->" + javaClass.simpleName
    private val retrofit by lazy { RestApi.ServiceBuilder.buildService(DataApi::class.java) }

    suspend fun getData(): Resource<MyDataResponse> {
        val data = retrofit.getData().awaitResponse()

        return if (data.isSuccessful) {
            Resource.Success(data.body()!!)
        } else {
            Resource.Error(Throwable(""))
        }
    }

    suspend fun getDataByCurrency(currency:String): Resource<MyDataResponse> {
        logD(currency)

        val data = retrofit.getDataByCurrency(currency).awaitResponse()

        return if (data.isSuccessful) {
            Resource.Success(data.body()!!)
        } else {
            Resource.Error(Throwable(""))
        }
    }

}