package com.project.weatherreport.presentation.screens.mainPage

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.project.weatherreport.domain.forecastModel.ForecastModel
import com.project.weatherreport.domain.forecastModel.Forecastday
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

                Row(modifier = modifier) {
                    ForecastFourDaysAfter(
                        forecast = data?.forecast?.forecastday?.drop(1),
                        modifier = modifier
                    )
                }
            }
        }


    } else LoadingAnimation(modifier = modifier)
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TodayForecast(data: ForecastModel?, modifier: Modifier) {
    val condition = data?.current?.condition?.text ?: ""
    val avgTempC = data?.forecast?.forecastday?.get(0)?.day?.avgtemp_c.toString()
    val state = data?.location?.country ?: ""
    val country = data?.location?.name ?: ""
    val maxTempC = data?.forecast?.forecastday?.get(0)?.day?.maxtemp_c.toString()
    val avgHumidity = data?.forecast?.forecastday?.get(0)?.day?.avghumidity.toString()
    val painter = rememberAsyncImagePainter(model = "https:" + data?.current?.condition?.icon)
    val maxWind = data?.forecast?.forecastday?.get(0)?.day?.maxwind_kph


    if (data != null) {
        Box(
            modifier = modifier

                .fillMaxWidth(1f)
                .height(250.dp)
                .padding(horizontal = 40.dp)
                .shadow(elevation = 3.dp, shape = CardDefaults.shape)
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
                        modifier = Modifier.padding(top = 0.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.Bottom,
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
                    Text(
                        text = "max: $maxTempC°C | hum: $avgHumidity φ | Max wind: $maxWind",
                        color = Color.White,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
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

private fun getCurrentDateWithDayAndMonthNames(): String {
    val currentDate = Calendar.getInstance()

    val dayOfWeekFormat = SimpleDateFormat("EEEE", Locale.getDefault())
    val dayOfWeek = dayOfWeekFormat.format(currentDate.time)

    val monthFormat = SimpleDateFormat("MMMM", Locale.getDefault())
    val month = monthFormat.format(currentDate.time)

    val year = currentDate.get(Calendar.YEAR)
    val day = currentDate.get(Calendar.DAY_OF_MONTH)

    return "$dayOfWeek, $day $month $year"
}


@Composable
fun ForecastFourDaysAfter(forecast: List<Forecastday>?, modifier: Modifier) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .height(500.dp)
            .fillMaxWidth(1f)
            .padding(horizontal = 40.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {

        forecast?.forEach { forecastDay ->
            val painter =
                rememberAsyncImagePainter(model = "https:" + forecastDay.day.condition.icon)
            Row(
                modifier = modifier
                    .clip(CardDefaults.shape)
                    .fillMaxWidth(1f)
                    .padding(bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround,
                    modifier = modifier
                        .shadow(elevation = 3.dp, shape = CardDefaults.shape)
                        .clip(CardDefaults.shape)
                        .background(Color.Gray)
                        .padding(10.dp)
                ) {
                    Text(text = "avg: ${forecastDay.day.avgtemp_c}°C", color = Color.White)
                    Text(text = "max: ${forecastDay.day.maxtemp_c}°C", color = Color.White)
                    Text(text = "hum: ${forecastDay.day.avghumidity} φ", color = Color.White)
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = modifier
                        .shadow(elevation = 3.dp, shape = CardDefaults.shape)
                        .clip(CardDefaults.shape)
                        .background(Color.Gray)
                        .padding(10.dp)

                ) {
                    Text(text = forecastDay.date, color = Color.White, fontSize = 14.sp)
                    Image(
                        painter = painter,
                        contentDescription = "Current condition in ${forecastDay.date} is ${forecastDay.day.condition.text}",
                        modifier = modifier.size(40.dp)
                    )
                    Text(
                        text = forecastDay.day.condition.text,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }

            }

        }
    }
}
