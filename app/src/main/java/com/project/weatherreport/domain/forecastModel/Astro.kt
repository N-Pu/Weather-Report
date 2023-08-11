
package com.project.weatherreport.domain.forecastModel
import com.google.gson.annotations.SerializedName
data class Astro(
    @SerializedName("is_moon_up") val is_moon_up: Int,
    @SerializedName("is_sun_up") val is_sun_up: Int,
    @SerializedName("moon_illumination") val moon_illumination: String,
    @SerializedName("moon_phase") val moon_phase: String,
    @SerializedName("moonrise") val moonrise: String,
    @SerializedName("moonset") val moonset: String,
    @SerializedName("sunrise") val sunrise: String,
    @SerializedName("sunset") val sunset: String
)