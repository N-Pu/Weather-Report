package com.project.weatherreport.repository

import com.project.weatherreport.domain.forecastModel.ForecastModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


private const val BASE_URL = "https://api.weatherapi.com/v1/"
private const val API_KEY = "bc32c8637e3b4cb681f165520231008"

interface WeatherApiService {

@GET("${BASE_URL}forecast.json?key=$API_KEY")
    suspend fun getForecast(
        @Query("q")  q: String,
        @Query("days") days: Int
    ): Response<ForecastModel>

    companion object {

        private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        private val httpClient = OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()

        val api: WeatherApiService by lazy {
            Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()
                .create(WeatherApiService::class.java)
        }
    }
}
