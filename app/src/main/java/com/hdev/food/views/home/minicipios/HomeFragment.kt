package com.hdev.food.views.home.minicipios

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.hdev.food.R
import com.hdev.food.base.BaseActivity
import com.hdev.food.interfaces.InterfaceGlobal
import com.hdev.food.models.ListModel
import com.hdev.food.repository.FirebaseRepository
import com.hdev.food.utils.Cons
import com.hdev.food.utils.DynamicBindingAdapter
import com.hdev.food.utils.extensions.*
import com.hdev.food.views.home.HomeHost
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
@ExperimentalCoroutinesApi
class HomeFragment : Fragment() {

    private val firebase: FirebaseRepository by inject()
    private val viewModel by viewModel<ListViewModel>()
    private var controller: LayoutAnimationController? = null
    private var optionsAdapter: DynamicBindingAdapter<ListModel>? = null
    private var main: InterfaceGlobal.mainGlobal = HomeHost()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home2, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        config()
        observerData()
    }

    private fun config() {
        Cons.activity = activity
        //logoChalate.setBackgroundBlur(R.drawable.chalate)
        controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
        viewModel.getData()
    }

    private fun observerData() {
        viewModel.dataResponse.observe(viewLifecycleOwner, Observer { data ->
            if (data.isNotEmpty()) {
                updateListOptions(data)
            } else {
                listRecycler.snackbar("Error al consultar datos.")
            }
        })
    }

    private fun updateListOptions(list: List<ListModel>) {
        optionsAdapter = viewModel.getAdapterOptions(list)
        optionsAdapter?.let { dynamic ->
            listRecycler?.configureRecyclerBinding(dynamic)
            listRecycler.setHasFixedSize(false)
            listRecycler.layoutAnimation = controller
            listRecycler.adapter?.notifyDataSetChanged()
            listRecycler.scheduleLayoutAnimation()
            dynamic.notifyDataSetChanged()
        }
    }
}
