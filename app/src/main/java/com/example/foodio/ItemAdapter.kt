package com.example.foodio

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.foodio.databinding.EachItemBinding

class ItemAdapter(
    private var items: List<Items>,
    private val onAddClick: (Items) -> Unit
):
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(){

    fun submitList(newItems: List<Items>) {
        Log.d("MenuAdapter", "submitList called with: $newItems")
        items = newItems
        notifyDataSetChanged() // Use DiffUtil for better performance in larger datasets
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = EachItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.btnAddToCart.setOnClickListener{
            onAddClick(item)
        }
    }

    inner class ItemViewHolder(private val binding: EachItemBinding):
        RecyclerView.ViewHolder(binding.root){
        val btnAddToCart: Button = binding.btnAddToCart

        fun bind(item: Items){
            binding.tvItemNameLabel.text = item.name 
            binding.tvPriceTag.text = item.price.toString()+"$"
        }
    }
}