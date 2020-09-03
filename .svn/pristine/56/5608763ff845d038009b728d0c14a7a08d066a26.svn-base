package com.ekenya.echama.responseCalback

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterResponse (@Json(name = "status") var status:Any? = "",
                             @Json(name = "data") var data:Any? = null
){
    @Json(name = "timestamp") var timestamp:String? =null
    @Json(name = "metadata") var metadata:String? = null
    @Json(name = "message") var message:String? = null
}