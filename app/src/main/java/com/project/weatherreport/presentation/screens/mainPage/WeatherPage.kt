package com.project.weatherreport.presentation.screens.mainPage

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.project.weatherreport.domain.forecastModel.ForecastModel
import com.project.weatherreport.domain.viewModel.ForecastViewModel
import com.project.weatherreport.presentation.animation.LoadingAnimation
import java.util.Locale


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
    viewModel: ForecastViewModel, searchText: String, isSearching: Boolean, modifier: Modifier
) {

    val data by viewModel.forecastData.collectAsStateWithLifecycle()
    val avgTempC = data?.forecast?.forecastday?.drop(1)?.joinToString("°C, ") { forecastDay ->
        forecastDay.day.avgtemp_c.toString()
    }
    val maxTempC = data?.forecast?.forecastday?.drop(1)?.joinToString("°C, ") { forecastDay ->
        forecastDay.day.maxtemp_c.toString()
    }
    val avgHumidity = data?.forecast?.forecastday?.drop(1)?.joinToString(" φ, ") { forecastDay ->
        forecastDay.day.avghumidity.toString()
    }

    val alpha by animateFloatAsState(
        targetValue = if (data != null) 1f else 0f,
        animationSpec = tween(durationMillis = 2500),
        label = "" // Увеличиваем длительность анимации
    )
    Row {
        OutlinedTextField(value = searchText,
            onValueChange = viewModel::onSearchTextChange,
            modifier = modifier
                .fillMaxWidth(1f)
                .padding(all = 10.dp),
            singleLine = true,
            maxLines = 1,
            label = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search label")
            })
    }

    if (!isSearching) {
        Column(
            modifier = modifier
                .alpha(alpha)
                .fillMaxWidth(1f)
                .fillMaxHeight(1f)
        ) {


            if (data != null) {
                Row(
                    modifier = modifier.padding(top = 100.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TodayForecast(data = data, modifier = modifier)
                }
                Row(
                    modifier = modifier
                        .fillMaxWidth(1f)
                        .height(60.dp)
                        .padding(horizontal = 40.dp, vertical = 10.dp)
                        .clip(CardDefaults.shape)
                        .background(Color.Gray),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(text = avgTempC ?: "", color = Color.White)

                    Log.d("ShowData", "avgTempC= $avgTempC")
                }
                Row(
                    modifier = modifier
                        .fillMaxWidth(1f)
                        .height(60.dp)
                        .padding(horizontal = 40.dp, vertical = 10.dp)
                        .clip(CardDefaults.shape)
                        .background(Color.Gray),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(text = maxTempC ?: "", color = Color.White)
                    Log.d("ShowData", "maxTempC= $maxTempC")
                }
                Row(
                    modifier = modifier
                        .fillMaxWidth(1f)
                        .height(60.dp)
                        .padding(horizontal = 40.dp, vertical = 10.dp)
                        .clip(CardDefaults.shape)
                        .background(Color.Gray),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(text = avgHumidity ?: "", color = Color.White)
                    Log.d("ShowData", "avgHumidity= $avgHumidity")
                }
            }
        }


    } else LoadingAnimation(modifier = modifier)
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodayForecast(data: ForecastModel?, modifier: Modifier) {
    val condition = data?.current?.condition?.text ?: ""
    val avgTempC = data?.forecast?.forecastday?.get(0)?.day?.avgtemp_c.toString()
    val state = data?.location?.country ?: ""
    val country = data?.location?.name ?: ""
    val maxTempC = data?.forecast?.forecastday?.get(0)?.day?.maxtemp_c.toString()
    val avgHumidity = data?.forecast?.forecastday?.get(0)?.day?.avghumidity.toString()
    val painter = rememberAsyncImagePainter(model = "https:" + data?.current?.condition?.icon)


    if (data != null) {
        Box(
            modifier = modifier
                .fillMaxWidth(1f)
                .height(250.dp)
                .padding(horizontal = 40.dp)
                .clip(CardDefaults.shape)
                .background(Color.Gray)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxHeight(1f)
                    .fillMaxWidth(1f)
                    .padding(16.dp)
            ) {
                Row {
                    Text(
                        text = "$state, $country",
                        color = Color.White,
                        fontSize = 30.sp,
                        modifier = modifier.basicMarquee(
                            iterations = Int.MAX_VALUE,
                            delayMillis = 2000,
                            initialDelayMillis = 2000,
                            velocity = 50.dp
                        )

                    )
                }


                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "$avgTempC°C",
                            color = Color.White,
                            fontSize = 44.sp,
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(
                                shadow = Shadow(
                                    offset = Offset(x = 0f, y = 6f),
                                    blurRadius = 5f,
                                    color = Color.Black.copy(alpha = 0.5f)
                                )
                            )
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.Top
                        modifier = Modifier.padding(top = 0.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.Bottom,
//                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Icon(
                                painter = painter,
                                contentDescription = "Weather icon",
                                modifier = modifier.size(70.dp),
                                tint = Color.Unspecified,


                                )
                        }
                    }

                }
                Row {
                    Text(
                        text = condition, color = Color.White, fontStyle = FontStyle.Italic
                    )

                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
//                    Column {
                    Text(
                        text = "max: $maxTempC°C | hum: $avgHumidity φ",
                        color = Color.White,
                        fontSize = 14.sp
                    )
//                    }
//                    Column {
//                        Text(text = "hum: $avgHumidity φ", color = Color.White, fontSize = 14.sp)
//                    }
                }




                Row(modifier = modifier.padding(top = 20.dp)) {
                    Text(
                        text = getCurrentDateWithDayAndMonthNames(),
                        fontSize = 20.sp,
                        color = Color.White,
                        style = TextStyle(
                            shadow = Shadow(
                                offset = Offset(x = 0f, y = 6f),
                                blurRadius = 5f,
                                color = Color.Black.copy(alpha = 0.5f)
                            )
                        )
                    )
                }
            }
        }
    }
}

fun getCurrentDateWithDayAndMonthNames(): String {
    val currentDate = Calendar.getInstance()

    val dayOfWeekFormat = SimpleDateFormat("EEEE", Locale.getDefault())
    val dayOfWeek = dayOfWeekFormat.format(currentDate.time)

    val monthFormat = SimpleDateFormat("MMMM", Locale.getDefault())
    val month = monthFormat.format(currentDate.time)

    val year = currentDate.get(Calendar.YEAR)
    val day = currentDate.get(Calendar.DAY_OF_MONTH)

    return "$dayOfWeek, $day $month $year"
}