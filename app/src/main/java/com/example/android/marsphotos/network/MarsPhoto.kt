package com.example.android.marsphotos.network

import com.squareup.moshi.Json

//we're using the Moshi to parse json response into kotlin useful objects
data class MarsPhoto ( val id: String,
                       @Json(name = "img_src") val imgSrcUrl: String)

