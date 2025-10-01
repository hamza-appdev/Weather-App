package com.example.weatherapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.WeatherApiServices.WeatherRetrofitInstance
import com.example.weatherapp.data.remote.dto.Constant
import com.example.weatherapp.data.remote.dto.WeatherModel
import com.example.weatherapp.domain.repository.weather.WeatherReposiroey
import com.example.weatherapp.util.Resultt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(): ViewModel() {
    private val getweatherapi = WeatherRetrofitInstance.api
   private val _weatherresut = mutableStateOf<Resultt<WeatherModel>>(Resultt.Initial)
    val weatherresut = _weatherresut



     fun getdata(city:String) {
         _weatherresut.value = Resultt.Loading
        viewModelScope.launch {
           val response = getweatherapi.getweather(Constant.apikey,city)
            try {
                if (response.isSuccessful){
                    response.body()?.let {
                        _weatherresut.value = Resultt.Success(it)
                    }
                }else{
                    _weatherresut.value = Resultt.Error("Failed to Loard Data${response.message()}")
                }
            }catch (e: Exception){
                _weatherresut.value = Resultt.Error(e.localizedMessage)
            }
        }


    }
}

