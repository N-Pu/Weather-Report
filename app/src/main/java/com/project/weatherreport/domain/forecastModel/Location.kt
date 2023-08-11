
package com.project.weatherreport.domain.forecastModel
import com.google.gson.annotations.SerializedName
data class Location(
    @SerializedName("country") val country: String,
    @SerializedName("lat") val lat: Double,
    @SerializedName("localtime") val localtime: String,
    @SerializedName("localtime_epoch") val localtime_epoch: Int,
    @SerializedName("lon") val lon: Double,
    @SerializedName("name") val name: String,
    @SerializedName("region") val region: String,
    @SerializedName("tz_id") val tz_id: String
)