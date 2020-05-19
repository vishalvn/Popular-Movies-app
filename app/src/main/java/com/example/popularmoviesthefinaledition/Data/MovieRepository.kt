package com.example.popularmoviesthefinaledition.Data

import com.example.popularmoviesthefinaledition.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MovieRepository {

    private val moviesAPI = create(BASE_URL)

    private fun create(baseUrl: String): MovieAPI {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(MovieAPI::class.java)
    }

    fun getMovies(sort_by: String, api_key: String) = moviesAPI.getMovie(sort_by, api_key)

    fun getTrailers(id: Int, api_key: String) = moviesAPI.getTrailer(id, api_key)

    fun getReviews(id: Int, api_key: String) = moviesAPI.getReview(id, api_key)

}