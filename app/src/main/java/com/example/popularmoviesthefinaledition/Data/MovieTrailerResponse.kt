package com.example.popularmoviesthefinaledition.Data

import com.example.popularmoviesthefinaledition.Constants.RESULTS
import com.google.gson.annotations.SerializedName

data class MovieTrailerResponse (
    @SerializedName(RESULTS)
    val trailerList: List<MovieTrailer>
)