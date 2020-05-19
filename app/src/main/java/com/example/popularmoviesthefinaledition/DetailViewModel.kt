package com.example.popularmoviesthefinaledition

import com.example.popularmoviesthefinaledition.Data.MovieAPI
import javax.inject.Inject

class DetailViewModel(private val movieDao: MovieDao):BaseViewModel() {
    @Inject
     lateinit var movieAPI: MovieAPI
    //val movieListAdapter =

}