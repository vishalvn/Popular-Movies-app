package com.example.popularmoviesthefinaledition

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.popularmoviesthefinaledition.Data.Movie

@Dao
interface MovieDao {
    @get:Query("SELECT * FROM movies")
    val all:List<Movie>

    @Insert
    fun insertAll(vararg users:Movie)
}