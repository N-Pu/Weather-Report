
package com.project.weatherreport.domain.forecastModel
import com.google.gson.annotations.SerializedName
data class ForecastModel(
    @SerializedName("alerts") val alerts: Alerts,
    @SerializedName("current") val current: Current,
    @SerializedName("forecast") val forecast: Forecast,
    @SerializedName("location") val location: Location
)