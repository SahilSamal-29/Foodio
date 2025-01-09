package com.example.foodio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(private val cartItems: ArrayList<Items>,
                  private val viewModel: MainViewModel) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return CartViewHolder(view)
    }

    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]
        holder.bind(item)
        holder.btnRemove.setOnClickListener {
            viewModel.removeItemFromCart(item)
            cartItems.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    class CartViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)  {
        private val title = itemView.findViewById<TextView>(R.id.tvItemName)
        private val price = itemView.findViewById<TextView>(R.id.tvPrice)
        internal val btnRemove = itemView.findViewById<Button>(R.id.btnRemove)
        fun bind(item: Items){
            title.text = item.name
            price.text = "${item.price}$"
        }
    }
}
