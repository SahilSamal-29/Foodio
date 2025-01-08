package com.example.foodio

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
//import kotlinx.android.parcel.Parcelize
@Parcelize
data class Menu(
    val menu: List<Items>
) : Parcelable

@Parcelize
data class Items(
    val available: Boolean,
    val category: String,
    val description: String,
    val id: Int,
    val name: String,
    val price: Double
) : Parcelable