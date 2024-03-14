package com.example.designcooktop.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Details(
    @SerializedName("columns")
    val columns: String,
    @SerializedName("flexiZones")
    val flexiZones: String,
    @SerializedName("positionLargeZOne")
    val positionLargeZOne: String,
    @SerializedName("rows")
    val rows: String,
    @SerializedName("rowsColumns")
    val rowsColumns: List<RowsColumn>
)