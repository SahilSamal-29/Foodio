package com.example.foodio

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodio.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ItemAdapter
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        adapter = ItemAdapter(items = emptyList(), onAddClick = { item ->
            viewModel.addItemToCart(item)
            Toast.makeText(this, "${item.name} added to cart", Toast.LENGTH_SHORT).show()
            })
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        viewModel.items.observe(this) { items ->
            if (!items.isNullOrEmpty()) {
                adapter.submitList(items)
                Log.d("RecyclerView", "RecyclerView updated with items: $items")
            } else {
                Log.d("RecyclerView", "No items found to display")
            }
        }

        viewModel.cartCount.observe(this){ count ->
            binding.tvCartCount.text = "$count items Added"
        }

//        viewModel.cartItems.observe(this){ cartItems ->
//            binding.btnViewCart.isEnabled = cartItems.isNotEmpty()
//        }

        binding.btnViewCart.setOnClickListener{
            val cartItems = viewModel.cartItems.value?: emptyList()
            val selectedItems = cartItems.toList()
            Log.e("MainActivity", "Selected items: $selectedItems")
            val intent = Intent(this, CartActivity::class.java)
            intent.putParcelableArrayListExtra("cartItems", ArrayList(selectedItems))
            startActivity(intent)
        }

    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}