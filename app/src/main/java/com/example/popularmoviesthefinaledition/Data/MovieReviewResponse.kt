package com.example.popularmoviesthefinaledition.Data

import com.example.popularmoviesthefinaledition.Constants.RESULTS
import com.google.gson.annotations.SerializedName

data class MovieReviewResponse (
    @SerializedName(RESULTS)
    val reviewsList: List<MovieReview>
)