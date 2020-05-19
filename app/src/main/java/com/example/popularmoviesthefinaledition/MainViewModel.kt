package com.example.popularmoviesthefinaledition

import android.provider.BaseColumns
import androidx.lifecycle.MutableLiveData
import com.example.popularmoviesthefinaledition.Data.Movie

class MainViewModel:BaseViewModel() {
    private val movieID = MutableLiveData<Int>()
    private val movieTitle = MutableLiveData<String>()
    private val movieOverview = MutableLiveData<String>()
    private val movieReleaseDate = MutableLiveData<String>()
    private val moviePosterPath = MutableLiveData<String>()
    private val movieVoteAvg = MutableLiveData<String>()

    fun bind(movie: Movie){
        movieID.value = movie.id
        movieTitle.value = movie.title
        movieOverview.value = movie.overview
        movieReleaseDate.value = movie.release_date
        moviePosterPath.value = movie.poster_path
        movieVoteAvg.value = movie.vote_average
    }
    fun getMovieID():MutableLiveData<Int>{
        return movieID
    }
    fun getMovieTitle():MutableLiveData<String>{
        return movieTitle
    }
    fun getMovieOverview():MutableLiveData<String>{
        return movieOverview
    }
    fun getMovieReleaseDate():MutableLiveData<String>{
        return movieReleaseDate
    }
    fun getMoviePosterPath():MutableLiveData<String>{
        return moviePosterPath
    }
    fun getMovieVoteAvg():MutableLiveData<String>{
        return movieVoteAvg
    }

}