package com.cesar.cesartestitpoint.utils

import android.content.Context
import android.net.ConnectivityManager
import com.cesar.cesartestitpoint.MyApplication
import com.cesar.cesartestitpoint.utils.Consts.RETROFIT.HEADER_CACHE_CONTROL
import com.cesar.cesartestitpoint.utils.Consts.RETROFIT.HEADER_PRAGMA
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

interface RestApi {

    object ServiceBuilder {
        private val client = OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).addInterceptor(
            offlineCacheInterceptor()
        ).addInterceptor(cacheInterceptor())
        .connectTimeout(60, TimeUnit.SECONDS).build()

        private val retrofit = Retrofit.Builder()
            .baseUrl(Consts.Constants.BASE_URL) // change this IP for testing by your actual machine IP
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        fun<T> buildService(service: Class<T>): T{
            return retrofit.create(service)
        }

        private fun offlineCacheInterceptor(): Interceptor {
            return Interceptor { chain ->
                var request = chain.request()
                if (!isConnected()) {
                    val cacheControl = CacheControl.Builder()
                        .maxStale(7, TimeUnit.DAYS)
                        .build()
                    request = request.newBuilder()
                        .removeHeader(HEADER_PRAGMA)
                        .removeHeader(HEADER_CACHE_CONTROL)
                        .cacheControl(cacheControl)
                        .build()
                }
                chain.proceed(request)
            }
        }

        private fun cacheInterceptor(): Interceptor {
            return Interceptor { chain ->
                val response = chain.proceed(chain.request())
                val cacheControl: CacheControl = if (isConnected()) {
                    CacheControl.Builder()
                        .maxAge(0, TimeUnit.SECONDS)
                        .build()
                } else {
                    CacheControl.Builder()
                        .maxStale(7, TimeUnit.DAYS)
                        .build()
                }
                response.newBuilder()
                    .removeHeader(HEADER_PRAGMA)
                    .removeHeader(HEADER_CACHE_CONTROL)
                    .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                    .build()
            }
        }

        private fun isConnected(): Boolean {
            try {
                val e = MyApplication.getAppContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetwork = e.activeNetworkInfo
                return activeNetwork != null && activeNetwork.isConnectedOrConnecting
            } catch (e: Exception) {

            }
            return false
        }
    }
}