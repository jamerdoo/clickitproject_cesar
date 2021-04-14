package com.cesar.cesartestitpoint.network.apiservice

import com.cesar.cesartestitpoint.domain.entity.MyDataResponse
import retrofit2.Call
import retrofit2.http.*

interface DataApi {

    @GET("/latest")
    fun getData(): Call<MyDataResponse>

    @GET("/latest")
    fun getDataByCurrency(@Query("base") currency : String): Call<MyDataResponse>
}