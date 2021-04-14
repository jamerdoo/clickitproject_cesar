package com.cesar.cesartestitpoint.domain.entity

import com.google.gson.annotations.SerializedName

class Motd (
    @SerializedName("msg") val msg : String,
    @SerializedName("url") val url : String
)