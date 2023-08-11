
package com.project.weatherreport.domain.forecastModel
import com.google.gson.annotations.SerializedName
data class Day(
    @SerializedName("air_quality") val air_quality: AirQualityX,
    @SerializedName("avghumidity") val avghumidity: Double,
    @SerializedName("avgtemp_c") val avgtemp_c: Double,
    @SerializedName("avgtemp_f") val avgtemp_f: Double,
    @SerializedName("avgvis_km") val avgvis_km: Double,
    @SerializedName("avgvis_miles") val avgvis_miles: Double,
    @SerializedName("condition") val condition: Condition,
    @SerializedName("daily_chance_of_rain") val daily_chance_of_rain: Int,
    @SerializedName("daily_chance_of_snow") val daily_chance_of_snow: Int,
    @SerializedName("daily_will_it_rain") val daily_will_it_rain: Int,
    @SerializedName("daily_will_it_snow") val daily_will_it_snow: Int,
    @SerializedName("maxtemp_c") val maxtemp_c: Double,
    @SerializedName("maxtemp_f") val maxtemp_f: Double,
    @SerializedName("maxwind_kph") val maxwind_kph: Double,
    @SerializedName("maxwind_mph") val maxwind_mph: Double,
    @SerializedName("mintemp_c") val mintemp_c: Double,
    @SerializedName("mintemp_f") val mintemp_f: Double,
    @SerializedName("totalprecip_in") val totalprecip_in: Double,
    @SerializedName("totalprecip_mm") val totalprecip_mm: Double,
    @SerializedName("totalsnow_cm") val totalsnow_cm: Double,
    @SerializedName("uv") val uv: Double
)