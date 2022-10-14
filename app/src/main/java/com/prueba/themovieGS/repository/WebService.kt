package com.prueba.themovieGS.repository

import com.prueba.themovieGS.manager.Constants
import com.prueba.themovieGS.data.entity.MovieList
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {
    @GET("movie/now_playing")
    suspend fun getNowplayingMovies(@Query("api_key") apiKey: String): MovieList

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("api_key") apiKey: String): MovieList
}

object RetrofitClient {
    val webService by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)
    }
}
