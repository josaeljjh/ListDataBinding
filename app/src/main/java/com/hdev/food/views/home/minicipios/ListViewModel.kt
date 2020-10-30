package com.hdev.food.views.home.minicipios

import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.api.ApiException
import com.google.gson.Gson
import com.hdev.food.App.Companion.context
import com.hdev.food.R
import com.hdev.food.databinding.ItemListBinding
import com.hdev.food.interfaces.InterfaceGlobal
import com.hdev.food.models.ListModel
import com.hdev.food.repository.FirebaseRepository
import com.hdev.food.repository.InfoRepository
import com.hdev.food.utils.Cons
import com.hdev.food.utils.DynamicBindingAdapter
import com.hdev.food.utils.NoInternetException
import com.hdev.food.utils.SingleLiveEvent
import com.hdev.food.utils.extensions.configureRecyclerBinding
import com.hdev.food.utils.extensions.gotoNavigate
import com.hdev.food.utils.extensions.launchAPIRequest
import com.hdev.food.utils.extensions.setSafeOnClickListener
import com.hdev.food.views.home.HomeHost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import java.util.ArrayList
import java.util.HashMap

/**
 * Created by Josael Hernandez on 3/3/20.
 * Contact : josaeljjh@gmail.com
 */

@ExperimentalCoroutinesApi
class ListViewModel( private val firebase: FirebaseRepository, private val repository: InfoRepository): ViewModel() {
    private var optionsAdapter: DynamicBindingAdapter<ListModel>? = null
    private var controller: LayoutAnimationController? = null
    private var networkListener: InterfaceGlobal.NetworkListener = HomeHost()
    private var main: InterfaceGlobal.mainGlobal = HomeHost()
    //private var data: ArrayList<ListModel>? = null
    val   dataResponse = MutableLiveData<ArrayList<ListModel>>()

 /*   val prueba = liveData(Dispatchers.IO){
        emit(Resource.Loading())

        try{
            useCase.getVersionCode().collect {
                emit(it)
            }

        }catch (e: Exception){
            emit(Resource.Failure(e))
            Log.e("ERROR:",e.message)
        }
    }*/

    fun getData() {
        launchAPIRequest {
            try {
                main.showLoading()
                //coroutines flow
                repository.getDataFirestone().collect { data ->
                    dataResponse.postValue(data)
                    main.dismissLoading()
                }
            } catch (e: ApiException) {
                main.dismissLoading()
                networkListener.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                main.dismissLoading()
                networkListener.onFailure(e.message!!)
            }
            main.dismissLoading()
        }
    }

    fun getAdapterOptions(list:List<ListModel>): DynamicBindingAdapter<ListModel> {
        var adapter: DynamicBindingAdapter<ListModel>? = null
        try {
            adapter = DynamicBindingAdapter(
                R.layout.item_list,
                list,
                fun(vh, view, data, posi) {
                    view as ItemListBinding
                    view.list = data
                    vh.itemView.setSafeOnClickListener {
                        val bundle = Bundle()
                        bundle.putParcelableArrayList(Cons.DATOS,data.restaurant)
                        view.cardview.gotoNavigate(R.id.food,bundle)
                    }

                })
        } catch (e: Exception) { }
        return adapter!!
    }
}