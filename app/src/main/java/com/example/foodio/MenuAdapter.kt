package com.example.foodio

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodio.databinding.EachItemBinding

class ItemAdapter(private val Items: List<Items>):
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = EachItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int = Items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(Items[position])
    }

    inner class ItemViewHolder(private val binding: EachItemBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(item: Items){
            binding.tvItemNameLabel.text = item.name 
            binding.tvPriceTag.text = item.price.toString() +"$"
        }
    }
}