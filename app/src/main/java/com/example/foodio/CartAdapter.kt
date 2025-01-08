package com.example.foodio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(private val cartItems: ArrayList<Items>) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return CartViewHolder(view)
    }

    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]
        holder.bind(item)
    }

    class CartViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)  {
        private val title = itemView.findViewById<TextView>(R.id.tvItemName)
        private val price = itemView.findViewById<TextView>(R.id.tvPrice)
        fun bind(item: Items){
            title.text = item.name
            price.text = "${item.price}$"
        }
    }
}
