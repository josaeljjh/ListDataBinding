package com.hdev.food

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.hdev.food.di.module.InjectModule.injectModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Created by Josael Hernandez on 2020-02-20.
 * Contact : josaeljjh@gmail.com
 */
class App:Application() {
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: App
            private set
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        context = instance.applicationContext
        startKoin {
            androidLogger()
            androidContext(this@App)
            // load modules here
            modules(injectModule)
        }
    }
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}