package com.hdev.food.utils

import android.content.Context
import android.net.ConnectivityManager
import com.hdev.food.App
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class NetworkConnectionInterceptor : Interceptor {
    private val mApplicationContext: Context = App.context

    private val isConnected: Boolean
        get() {
            val cm = mApplicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        if (!isConnected) {
            throw NoInternetException("No posees conexion a internet")
        }
        return chain.proceed(originalRequest)
    }
    //class NoNetworkException internal constructor() : RuntimeException("Please check Network Connection")
}