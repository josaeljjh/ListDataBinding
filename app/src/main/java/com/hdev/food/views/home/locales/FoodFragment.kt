


package com.hdev.food.views.home.locales

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.fragment.app.Fragment
import com.hdev.food.R
import com.hdev.food.models.RestaurantModel
import com.hdev.food.repository.FirebaseRepository
import com.hdev.food.utils.Cons
import com.hdev.food.utils.DynamicBindingAdapter
import com.hdev.food.utils.extensions.configureRecyclerBinding
import kotlinx.android.synthetic.main.fragment_food.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class FoodFragment : Fragment() {
    private val firebase: FirebaseRepository by inject()
    private val viewModel by viewModel<FoodViewModel>()
    private var controller: LayoutAnimationController? = null
    private var optionsAdapter: DynamicBindingAdapter<RestaurantModel>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    private fun initData(){
        controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
        arguments?.let { bundle ->
            val dataString = bundle.getParcelableArrayList<RestaurantModel>(Cons.DATOS)
            dataString?.let { updateListOptions(it) }
        }
    }

    private fun updateListOptions(list: List<RestaurantModel>) {
        optionsAdapter = viewModel.getAdapterOptions(list)
        optionsAdapter?.let { dynamic ->
            foodRecycler?.configureRecyclerBinding(dynamic)
            foodRecycler.setHasFixedSize(false)
            foodRecycler.layoutAnimation = controller
            foodRecycler.adapter?.notifyDataSetChanged()
            foodRecycler.scheduleLayoutAnimation()
            dynamic.notifyDataSetChanged()
        }
    }

}
