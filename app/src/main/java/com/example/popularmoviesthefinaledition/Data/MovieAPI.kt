package com.example.popularmoviesthefinaledition.Data

import com.example.popularmoviesthefinaledition.Constants.GET_MOVIE
import com.example.popularmoviesthefinaledition.Constants.GET_REVIEW
import com.example.popularmoviesthefinaledition.Constants.GET_TRAILER
import io.reactivex.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {
    @GET(GET_MOVIE)
    fun getMovie(@Query("sort_by") sort_by: String, @Query("api_key") api_key: String): Single<MovieResponse>

    @GET(GET_TRAILER)
    fun getTrailer(@Path("id") id: Int, @Query("api_key") api_key: String): Observable<MovieTrailerResponse>

    @GET(GET_REVIEW)
    fun getReview(@Path("id") id: Int, @Query("api_key") api_key: String): Observable<MovieReviewResponse>
}