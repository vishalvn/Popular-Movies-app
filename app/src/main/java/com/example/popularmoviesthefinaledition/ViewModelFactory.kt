package com.example.popularmoviesthefinaledition

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.popularmoviesthefinaledition.Fragments.MainFragment
import java.lang.IllegalArgumentException

class ViewModelFactory(private val activity:AppCompatActivity):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainFragment::class.java)) {
              val db = Room.databaseBuilder(activity.applicationContext,AppDataBase::class.java,"movies").build()
            @Suppress("UNCHECKED_CAST")
            return MainFragment() as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}