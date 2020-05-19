package com.example.popularmoviesthefinaledition.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.popularmoviesthefinaledition.Fragments.DetailFragment
import com.example.popularmoviesthefinaledition.R

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(
            R.id.container_detail,
            DetailFragment()
        )
        transaction.commit()
    }
}