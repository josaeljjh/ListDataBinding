package com.hdev.food.views.home

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.view.animation.LayoutAnimationController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import com.google.firebase.firestore.FirebaseFirestore
import com.hdev.food.App.Companion.context
import com.hdev.food.R
import com.hdev.food.base.BaseActivity
import com.hdev.food.interfaces.InterfaceGlobal
import com.hdev.food.models.ListModel
import com.hdev.food.repository.FirebaseRepository
import com.hdev.food.utils.Cons
import com.hdev.food.utils.Cons.sheetDialog
import com.hdev.food.utils.DynamicAdapter
import com.hdev.food.utils.DynamicBindingAdapter
import com.hdev.food.utils.extensions.*

import kotlinx.android.synthetic.main.activity_home_host.*
import org.koin.android.ext.android.inject

import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeHost() : BaseActivity(), InterfaceGlobal.mainGlobal, InterfaceGlobal.NetworkListener {

    private val firebase: FirebaseRepository by inject()
    private var optionsAdapter: DynamicBindingAdapter<ListModel>? = null
    private var controller: LayoutAnimationController? = null

    override fun getViewID(): Int = R.layout.activity_home_host
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_home_host)
        config()
    }

    private fun config() {
        setTransparentStatusBar()
        master.marginUpdate()
        bottomNavigationBar.configBottom()
        add_button.setOnClickListener {
            //showLoading()
            sheetDialog = Cons.activity?.connection()
        }
    }

    override fun showLoading() {
        sheetDialog = Cons.activity?.loading()
    }

    override fun dismissLoading() {
        sheetDialog.let { it?.dismiss() }
    }

    override fun onDestroy() {
        super.onDestroy()
        sheetDialog.let { it?.dismiss() }
    }

    override fun onSuccess(success: Boolean) {
    }

    override fun onFailure(message: String) {
        launch {
            Cons.activity?.master.snackbarConexion(message)
        }
    }

    override fun onError(message: String) {
        launch {
            Cons.activity?.master.snackbar(message)
        }
    }
}
