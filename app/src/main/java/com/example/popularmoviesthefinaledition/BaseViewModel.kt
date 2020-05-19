package com.example.popularmoviesthefinaledition

import androidx.lifecycle.ViewModel
import com.example.popularmoviesthefinaledition.Fragments.DetailFragment
import com.example.popularmoviesthefinaledition.Fragments.MainFragment
import dagger.internal.MapFactory.builder
import kotlin.text.Typography.dagger


abstract class BaseViewModel:ViewModel() {
    // private val injector: ViewModelInjector = ViewModelInjector
        // .m
       //  .build()

    private fun inject(){
        when(this){
        //    is  MainViewModel -> injector.inject(this)
         //   is DetailViewModel -> injector.inject(this)
        }
    }
}