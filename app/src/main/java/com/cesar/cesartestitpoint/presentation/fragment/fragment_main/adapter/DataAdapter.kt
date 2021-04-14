package com.cesar.cesartestitpoint.presentation.fragment.fragment_main.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cesar.cesartestitpoint.R
import com.cesar.cesartestitpoint.databinding.ItemsViewBinding
import com.cesar.cesartestitpoint.domain.entity.RatesMap
import com.cesar.cesartestitpoint.utils.Consts
import com.cesar.cesartestitpoint.utils.inflate
import kotlin.properties.Delegates

class DataAdapter (listItems: List<RatesMap> = emptyList(), private val listener: OnItemClickListener) : RecyclerView.Adapter<DataAdapter.ViewHolder>(){

    private var value: Double=0.0
    var listItems: List<RatesMap> by Delegates.observable(listItems){ _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            parent.inflate(
                R.layout.items_view
            ), listener
        )


    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listItems[position],value)
    }

    fun setAmount(value:Double){
        this.value= value
    }

    interface OnItemClickListener {
        fun onClickItem(item: RatesMap, TAG: String)
    }

    class ViewHolder(private val view: View, private val listener: OnItemClickListener) : RecyclerView.ViewHolder(view){

        private val binding = ItemsViewBinding.bind(view)

        fun bind (item: RatesMap,value: Double){
            with(binding){
                tvCurrency.text= item.currency
                tvAmount.text= String.format("%.2f",(item.value * value))

                itemView.setOnClickListener{
                  listener.onClickItem(item, Consts.Adapters.ALL)
                }
            }
        }
    }
}