package com.hdev.food.views.init

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hdev.food.R
import com.hdev.food.utils.extensions.launchActivity
import com.hdev.food.views.home.HomeHost
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            setTheme(R.style.SplashTheme)
        }else{
            setContentView(R.layout.activity_splash)
        }
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            launchActivity<HomeHost>(true)
        }
    }
}
