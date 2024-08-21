package com.example.foodio

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuAdapter
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.foodio.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    private lateinit var adapter: Adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        binding.recyclerView.adapter = Adapter(ViewModelProvider(this).get(MainViewModel::class.java))

        // Initialize Retrofit
        val retrofitbuilder = Retrofit.Builder()
            .baseUrl("https://66b398dd7fba54a5b7ed8a25.mockapi.io/api/v1/")     //without endpoint
            .addConverterFactory(GsonConverterFactory.create())
            .build()
//            .create(ApiInterface::class.java)
//        val retrofitdata = retrofitbuilder.getProductData()

        // Create API service
        val apiService = retrofitbuilder.create(ApiInterface::class.java)

        // Initialize repository
        val repository = MenuRepository(apiService)

        // Initialize ViewModel
        val viewModel: MainViewModel by viewModels {
            MainViewModelFactory(repository)
        }

        // Set up RecyclerView
        adapter = Adapter(viewModel)
        binding.recyclerView.adapter = adapter

        // Observe LiveData
//        viewModel.menuItems.observe(this) { items -> adapter.setItems(items) }

        // Fetch menu items
        viewModel.fetchMenuItems()

    }
}