
package com.project.weatherreport.domain.forecastModel
import com.google.gson.annotations.SerializedName
data class Alerts(
    @SerializedName("alert") val alert: List<Any>
)