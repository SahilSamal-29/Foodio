package com.example.foodio

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class MainViewModel(private val repository: MenuRepository): ViewModel(){

    val menuItems: LiveData<List<MenuItem>> = repository.menuItems

    fun fetchMenuItems() {
        repository.fetchMenuItems()
    }

    fun incrementItemQuantity(item: MenuX) {
        item.quantity += 1
        // Update LiveData with the new list to trigger UI updates
        repository.fetchMenuItems()
    }
}