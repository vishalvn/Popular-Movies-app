package com.example.popularmoviesthefinaledition.Data

import com.example.popularmoviesthefinaledition.Constants.RESULTS
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName(RESULTS)
    val movieList: List<Movie>
)