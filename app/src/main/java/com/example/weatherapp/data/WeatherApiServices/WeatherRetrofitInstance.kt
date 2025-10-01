package com.example.weatherapp.data.WeatherApiServices

import com.example.weatherapp.domain.repository.weather.WeatherReposiroey
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object WeatherRetrofitInstance{
//    https://api.weatherapi.com/
    val api: WeatherReposiroey by lazy {
    Retrofit.Builder()
        .baseUrl("https://api.weatherapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherReposiroey::class.java)
}
}