package com.cesar.cesartestitpoint.domain.entity

import com.google.gson.annotations.SerializedName

class MyDataResponse (
    @SerializedName("motd") val motd : Motd,
    @SerializedName("success") val success : Boolean,
    @SerializedName("base") val base : String,
    @SerializedName("date") val date : String,
    @SerializedName("rates") val rates : Rates
)
