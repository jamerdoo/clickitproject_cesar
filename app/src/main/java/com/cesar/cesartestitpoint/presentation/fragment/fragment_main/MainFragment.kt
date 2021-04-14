package com.cesar.cesartestitpoint.presentation.fragment.fragment_main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cesar.cesartestitpoint.R
import com.cesar.cesartestitpoint.databinding.MainFragmentBinding
import com.cesar.cesartestitpoint.domain.Resource
import com.cesar.cesartestitpoint.domain.entity.RatesMap
import com.cesar.cesartestitpoint.presentation.fragment.fragment_main.adapter.DataAdapter
import com.cesar.cesartestitpoint.utils.Consts
import com.cesar.cesartestitpoint.utils.Logger
import com.cesar.cesartestitpoint.utils.printToast
import com.google.gson.Gson

class MainFragment : Fragment(), Logger, DataAdapter.OnItemClickListener {

    private lateinit var binding: MainFragmentBinding
    override val nameClass: String get() = "--->"+javaClass.simpleName
    private lateinit var viewModel: MainViewModel
    private val initAdapter = DataAdapter(emptyList(), this)
    private var itemList: MutableList<RatesMap> = arrayListOf()
    private var currency= Consts.Constants.defaultCurrency

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = MainFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
    }

    private fun initComponents() {
        with(binding){
            recycler.adapter= initAdapter
            btnCalcular.setOnClickListener {
                if(etAmount.text.toString().isEmpty()){
                    requireActivity().printToast("Introduzca el valor")
                    return@setOnClickListener
                }
                if(itemList.isNotEmpty()){
                    logD(Gson().toJson(itemList))
                    initAdapter.setAmount(etAmount.text.toString().toDouble())
                    initAdapter.listItems= itemList
                }
            }
        }

        viewModel.liveData.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Loading -> {
                    showProgress(true)
                }
                is Resource.Success -> {
                    showProgress(false)
                    logD(Gson().toJson(resource.data))
                    updateSpinnerData(resource.data,currency)
                    this.itemList = resource.data as MutableList<RatesMap>
                }
                is Resource.Error -> {
                    showProgress(false)
                    showSnackBarFailed(getString(R.string.handle_error))
                }
            }
        })

        viewModel.liveDataByCurrency.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Loading -> {
                    showProgress(true)
                }
                is Resource.Success -> {
                    showProgress(false)
                    updateSpinnerData(resource.data,currency)
                    this.itemList = resource.data as MutableList<RatesMap>
                    initAdapter.setAmount(binding.etAmount.text.toString().toDouble())
                    initAdapter.listItems= itemList
                }
                is Resource.Error -> {
                    showProgress(false)
                    showSnackBarFailed(getString(R.string.handle_error))
                }
            }
        })

        viewModel.getData(Consts.Constants.defaultCurrency)
    }

    private fun updateSpinnerData(data: ArrayList<RatesMap>?, currency:String) {
        logD("Currency spinner $currency")
        val list=  mutableListOf<String>()
        binding.spinnerCategorias.setItems(list)

        var pos= 0
        data?.forEachIndexed { index, ratesMap ->
            if(currency == ratesMap.currency){
                pos=index
            }
            list.add(ratesMap.currency)
        }


        binding.spinnerCategorias.setItems(list)
        binding.spinnerCategorias.selectedIndex=pos
        binding.spinnerCategorias.setOnItemSelectedListener { _, _, _, item ->
            this.currency=item.toString()
            if(binding.etAmount.text.isNotEmpty() && binding.etAmount.text.toString().toDouble()>0) {
                viewModel.getDataByCurrency(item.toString())
            }
        }
    }

    private fun showProgress(control: Boolean) {binding.progress.visibility= (if (control) View.VISIBLE else View.GONE)}

    private fun showSnackBarFailed(message: String) {
        requireActivity().printToast(message)
    }

    override fun onClickItem(item: RatesMap, TAG: String) {
        when(TAG){
            Consts.Adapters.ALL->{
                requireActivity().printToast(item.currency.plus(": ").plus(item.value.toString()))
            }
        }
    }
}