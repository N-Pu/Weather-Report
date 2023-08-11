package com.project.weatherreport.domain.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.weatherreport.repository.WeatherApiService

class MyViewModelFactory(private val weatherApiService: WeatherApiService) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ForecastViewModel::class.java) -> ForecastViewModel(
                weatherApiService
            ) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}