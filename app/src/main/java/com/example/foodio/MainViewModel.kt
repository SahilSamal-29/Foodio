package com.example.foodio

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MainViewModel: ViewModel() {
    private val _items = MutableLiveData<List<Items>>()
    val items: LiveData<List<Items>> get() = _items

    private val _cartCount = MutableLiveData<Int>(0)
    val cartCount: LiveData<Int> = _cartCount

//    private val _cartItems = MutableLiveData<MutableList<Items>>(mutableListOf())
//    val cartItems: MutableLiveData<MutableList<Items>> get() = _cartItems
    private val _cartItems = MutableLiveData<MutableList<Items>>(mutableListOf())
    val cartItems: MutableLiveData<MutableList<Items>> get() = _cartItems

    init{
        Log.d("MainViewModel", "ViewModel initialized, fetching items...")
        fetchItems()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun fetchItems(){
        GlobalScope.launch{
            try {
                Log.d("MainViewModel", "Fetching items from API...")
                val response = RetrofitInstance.apiService.fetchItems()
                Log.d("MainViewModel", "Fetched items from API: ${response.menu}")
                _items.postValue(response.menu)
            } catch (e: HttpException) {
                Log.e("MainViewModel", "HTTP Exception: ${e.code()} - ${e.message()}")
            } catch (e: IOException) {
                Log.e("MainViewModel", "Network Error: ${e.message}")
            } catch (e: Exception) {
                Log.e("MainViewModel", "Unexpected Error: ${e.message}")
            }
        }
    }

    fun addItemToCart(item: Items) {
        _cartCount.value = (_cartCount.value ?: 0) + 1
        // ?: elvis operator
        // if the value is null then this will return 0
        val currentCart = _cartItems.value ?: mutableListOf()
        currentCart.add(item)
        _cartItems.value = currentCart
        Log.d("MainViewModel", "Added item to cart: $item")
        Log.d("MainViewModel", "Updated cart items: ${_cartItems.value}")
    }

    // Remove an item from the cart
    fun removeItemFromCart(item: Items) {
        val currentCart = _cartItems.value ?: mutableListOf()
        if (currentCart.contains(item)) {
            currentCart.remove(item)
            _cartItems.value = currentCart
        }
    }

    // Clear the cart
    fun clearCart() {
        _cartItems.value = mutableListOf()
    }
}