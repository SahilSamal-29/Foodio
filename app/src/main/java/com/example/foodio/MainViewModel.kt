package com.example.foodio

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val _items = MutableLiveData<List<Items>>()
    val items: LiveData<List<Items>> = _items

    init{
        fetchItems()
    }

    private fun fetchItems(){
        viewModelScope.launch{
            try{
                val response = RetrofitInstance.apiService.fetchItems()
                _items.postValue(response.menu)
            }catch (e: Exception){
                Log.e("MainViewModel", "Error fetching items", e)
            }
        }
    }
}