package com.cesar.cesartestitpoint.domain.entity

import com.google.gson.annotations.SerializedName

class RatesMap (
    @SerializedName("currency") val currency : String,
    @SerializedName("value") val value : Double
)