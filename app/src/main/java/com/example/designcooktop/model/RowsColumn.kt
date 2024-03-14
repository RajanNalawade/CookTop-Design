package com.example.designcooktop.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class RowsColumn(
    @SerializedName("circleType")
    val circleType: String,
    @SerializedName("isCircle")
    val isCircle: String,
    @SerializedName("isFlexi")
    val isFlexi: String,
    @SerializedName("isRectangle")
    val isRectangle: String,
    @SerializedName("isZoneAvailable")
    val isZoneAvailable: String
)