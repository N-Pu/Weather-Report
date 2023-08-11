package com.project.weatherreport.presentation.screens.mainPage

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.project.weatherreport.domain.viewModel.ForecastViewModel


@Composable
fun ShowWeatherScreen(viewModelProvider: ViewModelProvider, modifier: Modifier) {

    val viewModel = viewModelProvider[ForecastViewModel::class.java]
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    val isSearching by viewModel.isPerformingSearch.collectAsStateWithLifecycle()
    ShowData(
        viewModel = viewModel,
        searchText = searchText,
        isSearching = isSearching,
        modifier = modifier
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShowData(
    viewModel: ForecastViewModel,
    searchText: String,
    isSearching: Boolean,
    modifier: Modifier
) {

    val data by viewModel.forecastData.collectAsStateWithLifecycle()
    val avgTempC = data?.forecast?.forecastday?.joinToString(", ") { forecastDay ->
        forecastDay.day.avgtemp_c.toString()
    }
    val maxTempC = data?.forecast?.forecastday?.joinToString(", ") { forecastDay ->
        forecastDay.day.maxtemp_c.toString()
    }
    val avgHumidity = data?.forecast?.forecastday?.joinToString(", ") { forecastDay ->
        forecastDay.day.avghumidity.toString()
    }
    val painter = rememberAsyncImagePainter(model = "https:" + data?.current?.condition?.icon)
    Column {
        Row {
            OutlinedTextField(
                value = searchText,
                onValueChange = viewModel::onSearchTextChange,
                modifier = modifier.fillMaxWidth(1f),
                singleLine = true,
            )
        }
    }
    if (!isSearching) {
        Row(
            modifier = modifier.fillMaxHeight(0.8f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = data?.current?.condition?.text ?: "N/A")
                Log.d("ShowData", "text= " + data?.current?.condition?.text)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Image(
                    painter = painter,
                    contentDescription = "Weather Icon",
                    modifier = modifier.size(60.dp, 60.dp)
                )


                Log.d("ShowData", "icon= " + data?.current?.condition?.icon)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = avgTempC ?: "N/A")

                Log.d("ShowData", "avgTempC= $avgTempC")

            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = maxTempC ?: "N/A")
                Log.d("ShowData", "maxTempC= $maxTempC")
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = avgHumidity ?: "N/A")
                Log.d("ShowData", "avgHumidity= $avgHumidity")
            }


        }
    }
}