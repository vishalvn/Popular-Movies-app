package com.example.popularmoviesthefinaledition

import com.example.popularmoviesthefinaledition.Data.MovieAPI
import javax.inject.Inject

class MovieViewModel() {
    @Inject
    lateinit var movieAPI: MovieAPI
}