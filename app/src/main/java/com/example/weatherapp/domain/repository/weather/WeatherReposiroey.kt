package com.example.weatherapp.domain.repository.weather

import com.example.weatherapp.data.remote.dto.WeatherModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherReposiroey {
//    v1/current.json
    @GET("v1/current.json")
    suspend fun getweather(
        @Query("key") apikey: String,
        @Query("q") city: String
    ): Response<WeatherModel>
}