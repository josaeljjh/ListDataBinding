package com.hdev.food.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.hdev.food.utils.BottomDialogFragment
import com.hdev.food.utils.Cons
import com.hdev.food.utils.extensions.loading

/**
 * Created by Josaél Hernández on 7/9/20.
 * Contact : josaeljjh@gmail.com
 */
abstract class BaseActivity : AppCompatActivity() {

    var sheetDialog: BottomDialogFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getViewID())
    }

    protected abstract fun getViewID(): Int

    fun showProgress() {
        sheetDialog = loading()
    }

    fun hideProgress() {
        sheetDialog.let { it?.dismiss() }
    }

    override fun onDestroy() {
        super.onDestroy()
        sheetDialog.let { it?.dismiss() }
    }

}