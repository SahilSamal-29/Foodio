package com.example.foodio

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// The repository will manage data operations.
// It will fetch data from the API and store it.

class MenuRepository(private val apiService: ApiInterface) {
    private val _menuItems = MutableLiveData<List<MenuItem>>()
    val menuItems: LiveData<List<MenuItem>> get() = _menuItems

    fun fetchMenuItems() {
        apiService.getProductData().enqueue(object : Callback<MenuItem?> {
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