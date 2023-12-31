package com.project.weatherreport.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.project.weatherreport.domain.viewModel.MyViewModelFactory
import com.project.weatherreport.presentation.screens.mainPage.ShowWeatherScreen
import com.project.weatherreport.presentation.theme.WeatherReportTheme
import com.project.weatherreport.repository.WeatherApiService

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val myViewModelFactory =
                MyViewModelFactory(weatherApiService = WeatherApiService.api)
            val viewModelProvider = ViewModelProvider(this, myViewModelFactory)
            WeatherReportTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShowWeatherScreen(viewModelProvider, Modifier)
                }
            }
        }
    }
}



