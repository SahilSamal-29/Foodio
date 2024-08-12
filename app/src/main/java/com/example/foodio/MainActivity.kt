package com.example.foodio

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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
        binding.recyclerView.adapter = Adapter()

        val retrofitbuilder = Retrofit.Builder()
            .baseUrl("https://66b398dd7fba54a5b7ed8a25.mockapi.io/api/v1/")     //without endpoint
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
        val retrofitdata = retrofitbuilder.getProductData()

        retrofitdata.enqueue(object : Callback<MenuItem?> {
            override fun onResponse(p0: Call<MenuItem?>, p1: Response<MenuItem?>) {
                var responseBody = p1.body() // we’ll get main parameters of api
                val productsList = responseBody?.menu!!  //if not null then only proceed

                val collectDataInSB = StringBuilder()
//
                for(myData in productsList){
                    collectDataInSB.append(myData.name + " ")
                }

            }

            override fun onFailure(p0: Call<MenuItem?>, p1: Throwable) {
                Log.d("Main Activity", "onFailure: " + p1.message)
            }
        })
    }
}