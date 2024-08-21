package com.example.foodio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodio.databinding.ActivityMainBinding
import com.example.foodio.databinding.EachItemBinding

class Adapter(private val viewModel: MainViewModel) :
    RecyclerView.Adapter<Adapter.MainViewHolder>() {
//    val binding: EachItemBinding = EachItemBinding.inflate(layoutInflater)
    private var items: List<MenuX> = listOf()
    private var cartItems: MutableList<MenuItem> = mutableListOf()
    private var itemCount: Int = 0

//    fun setItems(items: List<MenuItem>) {
//        this.items = items
//        notifyDataSetChanged()
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = EachItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: Adapter.MainViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

//    class ViewHolder : RecyclerView.ViewHolder {
//        constructor(itemView: View) : super(itemView) {
//        }
//
//
//    }

    inner class MainViewHolder(private val binding: EachItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MenuX) {
            binding.tvItemNameLabel.text = item.name
            binding.tvPriceTag.text = "${item.price} Rs"
//            binding.quantityTextView.text = item.quantity.toString()
            binding.btnAddToCart.setOnClickListener {
                viewModel.incrementItemQuantity(item)
            }
        }


    }
}