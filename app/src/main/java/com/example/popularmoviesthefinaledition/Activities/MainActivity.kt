package com.example.popularmoviesthefinaledition.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.popularmoviesthefinaledition.Fragments.MainFragment
import com.example.popularmoviesthefinaledition.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(
            R.id.container,
            MainFragment()
        )
        transaction.commit()
    }
}
