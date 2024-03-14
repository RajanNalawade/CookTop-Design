package com.example.designcooktop.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CookTopsItem(
    @SerializedName("details")
    val details: Details,
    @SerializedName("name")
    val name: String
)