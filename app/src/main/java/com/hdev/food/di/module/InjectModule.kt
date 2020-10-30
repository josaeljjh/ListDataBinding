package com.hdev.food.di.module

import com.hdev.food.repository.FirebaseRepository
import com.hdev.food.repository.InfoRepository
import com.hdev.food.views.home.locales.FoodViewModel
import com.hdev.food.views.home.minicipios.ListViewModel

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


/**
 * Created by Josael Hernandez on 2020-02-20.
 * Contact : josaeljjh@gmail.com
 */

object InjectModule {

    val injectModule: Module = module {
        single { FirebaseRepository() }
        single { InfoRepository(get()) }
        viewModel { ListViewModel(get(),get()) }
        viewModel { FoodViewModel(get(),get()) }
    }
}