package com.hdev.food.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Josaél Hernández on 4/12/20.
 * Contact : josaeljjh@gmail.com
 */

@Parcelize
data class ListModel(
    val name: String,
    val restaurant: ArrayList<RestaurantModel>
) : Parcelable

@Parcelize
data class RestaurantModel(
    val image: String,
    val name: String,
    val direction: String,
    val phone: String,
    val horary: String,
    val ubication: String
) : Parcelable