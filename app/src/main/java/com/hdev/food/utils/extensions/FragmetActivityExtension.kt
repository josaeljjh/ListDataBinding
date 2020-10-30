package com.hdev.food.utils.extensions


import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.hdev.food.R
import com.hdev.food.utils.BottomDialogFragment
import com.hdev.food.utils.Cons
import kotlinx.android.synthetic.main.sheet_connection.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


//Activity StatusBarColor
fun FragmentActivity.configureStatusBarColor(
    color: Int = R.color.colorPrimary,
    darkIcons: Boolean = false
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        window.statusBarColor = ContextCompat.getColor(this, color)
        var flags = window.decorView.systemUiVisibility
        flags = if (darkIcons) {
            flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
        window.decorView.systemUiVisibility = flags
    }
}

fun FragmentActivity.launch(action: suspend () -> Unit) {
    lifecycleScope.launch {
        withContext(Dispatchers.Main) {
            action.invoke()
        }
    }
}

fun FragmentActivity.setTransparentStatusBar() {
    window.apply {
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        statusBarColor = Color.TRANSPARENT
    }
    darkIcons()
}
fun FragmentActivity.darkIcons() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        var flags = window.decorView.systemUiVisibility
        val currentNightMode = resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)
        flags = if(currentNightMode == Configuration.UI_MODE_NIGHT_NO){
            //flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }else{
            flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
        window.decorView.systemUiVisibility = flags
    }
}


//Inline function for starting an Activity
inline fun <reified T : FragmentActivity> FragmentActivity.launchActivity(
    closeCurrent: Boolean = false,
    noinline init: Intent.() -> Unit = {}
) {
    val i = Intent(this, T::class.java)
    i.init()
    startActivity(i)
    overridePendingTransition(R.anim.slide_in_right, android.R.anim.fade_out)
    if (closeCurrent) {
        finish()
    }
}

fun FragmentActivity.loading(): BottomDialogFragment {
    val mBottomSheetDialog = BottomDialogFragment(
        R.layout.sheet_dialog,
        fun(_) {
            //view.Title.text = "Qué servicio programarás"
        })
    configureDialogFragment(mBottomSheetDialog, true, cancelable = false)
    return mBottomSheetDialog
}

fun FragmentActivity.connection(): BottomDialogFragment {
    val mBottomSheetDialog = BottomDialogFragment(
        R.layout.sheet_connection,
        fun(view) {
            view.btnActivar.setSafeOnClickListener {
                startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                //Cons.sheetDialog.let { it?.dismiss() }
            }
            view.btnClose.setSafeOnClickListener {
                Cons.sheetDialog.let { it?.dismiss() }
            }
        })
    configureDialogFragment(mBottomSheetDialog, true, cancelable = true)
    return mBottomSheetDialog
}

fun FragmentActivity.configureDialogFragment(
    dialog: Any,
    isBottomSheet: Boolean = true,
    cancelable: Boolean = false
) {
    val receivedDialog =
        if (isBottomSheet) dialog as BottomDialogFragment else dialog as DialogFragment
    receivedDialog.setStyle(
        DialogFragment.STYLE_NO_TITLE,
        if (isBottomSheet) {
            R.style.BottomSheetDialog
        } else {
            android.R.style.Theme_Black_NoTitleBar_Fullscreen
        }
    )
    receivedDialog.isCancelable = cancelable
    receivedDialog.show(supportFragmentManager, "")
}