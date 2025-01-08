package com.example.foodio

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cart)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val recyclerView = findViewById<RecyclerView>(R.id.rv)
//        val cartItems = intent.getParcelableExtra<Items>("cartItems") ?: emptyList()
//        val cartItems = intent.getStringArrayListExtra("cartItems")
        val cartItems: ArrayList<Items> = intent.extras?.getParcelableArrayList("cartItems")!!

//        val cartItems = intent.getParcelableArrayListExtra<Items>("cartItems") ?: emptyList()
        val cartAdapter = CartAdapter(cartItems)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = cartAdapter

        Log.d("CartActivity", "Selected items: $cartItems")

    // Check if selected items are not null
//        if (!cartItems.isNullOrEmpty()) {
//            cartAdapter = CartAdapter(cartItems) // Pass only selected items
//            recyclerView.adapter = cartAdapter // Attach adapter
//        } else {
//            Toast.makeText(this, "No items added to the cart!", Toast.LENGTH_SHORT).show()
//        }
    }
}