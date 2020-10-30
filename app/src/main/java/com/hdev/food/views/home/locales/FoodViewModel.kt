package com.hdev.food.views.home.locales

import android.view.View
import androidx.lifecycle.ViewModel
import com.hdev.food.R
import com.hdev.food.databinding.ItemFoodBinding
import com.hdev.food.models.RestaurantModel
import com.hdev.food.repository.FirebaseRepository
import com.hdev.food.repository.InfoRepository
import com.hdev.food.utils.DynamicBindingAdapter
import com.hdev.food.utils.extensions.setSafeOnClickListener

/**
 * Created by Josaél Hernández on 4/29/20.
 * Contact : josaeljjh@gmail.com
 */

class FoodViewModel(private val firebase: FirebaseRepository, private val repository: InfoRepository): ViewModel() {

    fun getAdapterOptions(list: List<RestaurantModel>): DynamicBindingAdapter<RestaurantModel> {
        var adapter: DynamicBindingAdapter<RestaurantModel>? = null
        try {
            adapter = DynamicBindingAdapter(
                R.layout.item_food,
                list,
                fun(vh, view, data, posi) {
                    view as ItemFoodBinding
                    view.food = data
                    vh.itemView.setSafeOnClickListener {
                        //view.footer.visibility = View.VISIBLE
                    }
                })
        } catch (e: Exception) { }
        return adapter!!
    }
}