package com.example.foodio

data class Menu(
    val menu: List<Items>
)

data class Items(
    val available: Boolean,
    val category: String,
    val description: String,
    val id: Int,
    val name: String,
    val price: Double
)