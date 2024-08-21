package com.example.foodio

data class MenuX(
    val available: Boolean,
    val category: String,
    val description: String,
    val id: Int,
    val name: String,
    val price: Double,
    var quantity: Int = 0
)