package com.cesar.cesartestitpoint.presentation.fragment.fragment_main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cesar.cesartestitpoint.MyApplication
import com.cesar.cesartestitpoint.domain.Resource
import com.cesar.cesartestitpoint.domain.entity.RatesMap
import com.cesar.cesartestitpoint.domain.entity.local.LocalRates
import com.cesar.cesartestitpoint.domain.repository.InfoDataSource
import com.cesar.cesartestitpoint.domain.repository.InfoRepository
import com.cesar.cesartestitpoint.domain.usercase.GetInfoByCurrency
import com.cesar.cesartestitpoint.domain.usercase.GetInitInfo
import com.cesar.cesartestitpoint.network.InfoDataSourceImpl
import com.cesar.cesartestitpoint.network.RetrofitProvider
import com.cesar.cesartestitpoint.roomDB.RatesDatabase
import com.cesar.cesartestitpoint.roomDB.RatesRepository
import com.cesar.cesartestitpoint.utils.Logger
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

class MainViewModel : ViewModel() , Logger, RoomResponse {

    override val nameClass: String get() = "--->"+javaClass.simpleName
    private var infoDataSource: InfoDataSource = InfoDataSourceImpl(RetrofitProvider())
    private var getInfo = GetInitInfo(InfoRepository(infoDataSource))
    private var getCurrencyInfo = GetInfoByCurrency(InfoRepository(infoDataSource))

    private val dao = RatesDatabase.getInstance(MyApplication.getApp()).dao
    private val dbRepository = RatesRepository(dao)
    private val roomResponse:RoomResponse=this

    var liveData = MutableLiveData<Resource<ArrayList<RatesMap>>>()
    var liveDataByCurrency = MutableLiveData<Resource<ArrayList<RatesMap>>>()

    fun getData(currency: String){
        searchCurrency(currency)
    }

    fun getDataByCurrency(currency: String) {
        liveDataByCurrency.value = Resource.Loading()

        getCurrencyInfo.invoke(params = currency,onResult = { it ->
            val str = Gson().toJson(it.data!!.rates).replace("{","").replace("}","").replace("\"","")
            val filterList: MutableList<RatesMap> = arrayListOf()

            str.split(",").forEach {
                val aux = it.split(":")
                filterList.add(RatesMap(aux[0],aux[1].toDouble()))
            }

            liveDataByCurrency.value = Resource.Success(ArrayList(filterList))

        }, onError = {
            logD(it.localizedMessage ?: "")
            logD(it.message ?: "")
        })
    }

    private fun searchCurrency(currency: String){
        var localRates: LocalRates?

        viewModelScope.launch(Dispatchers.Main) {
            localRates = dbRepository.getDataById(currency)
            if(localRates!=null && localRates!!.base==currency){
                roomResponse.onSuccess(localRates!!)
            }else{
                roomResponse.onFailed(currency)
            }
        }
    }

    /**
     * Corrutines example
     * 1. Scope
     * 2. Launch
     * 3. Dispatchers (Params IO,Main,Default)
     * 4. withcontext (Cambiar los hilos de ejecucion)
     * */
    private fun searchCurrencyCorrutines(currency: String){
        var localRates: LocalRates?
        viewModelScope.launch(Dispatchers.Main) {
            localRates = withContext(Dispatchers.IO) {dbRepository.getDataById(currency)}
            localRates?.let {
                if(it.base==currency) onSuccess(it) else onFailed(currency)
            }
        }
    }

    override fun onSuccess(localRates: LocalRates) {
        logD(Gson().toJson("onSuccess $localRates"))
        val objectList = Gson().fromJson(localRates.data, Array<RatesMap>::class.java).asList()
        liveData.value = Resource.Success(ArrayList(objectList))
    }

    override fun onFailed(currency: String) {
        logD(Gson().toJson("onFailed $currency"))
        liveData.value = Resource.Loading()

        getInfo.invoke(onResult = { it ->
            //logD(Gson().toJson(it.data!!.rates))
            val str = Gson().toJson(it.data!!.rates).replace("{","").replace("}","").replace("\"","")
            val filterList: MutableList<RatesMap> = arrayListOf()

            str.split(",").forEach {
                val aux = it.split(":")
                filterList.add(RatesMap(aux[0],aux[1].toDouble()))
            }

            insert(LocalRates(currency,Gson().toJson(filterList)))

            liveData.value = Resource.Success(ArrayList(filterList))

        }, onError = {
            logD(it.localizedMessage ?: "")
            logD(it.message ?: "")
        })
    }

    private fun insert(localRates: LocalRates) = viewModelScope.launch {
        val result = dbRepository.insert(localRates)
        if (result > -1) {
            logD("insert success")
        } else {
            logD("insert failed")
        }
    }



}

interface RoomResponse {
    fun onSuccess(localRates: LocalRates)
    fun onFailed(currency: String)
}