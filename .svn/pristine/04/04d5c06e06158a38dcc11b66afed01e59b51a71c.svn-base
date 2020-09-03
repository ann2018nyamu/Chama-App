package com.ekenya.echama.responseCalback

import com.ekenya.echama.model.Country
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CountryResponse (@Json(name = "status") var status:String? = "", @Json(name = "data") var data:List<Country>?){

    @Json(name = "message") var message:String?=null
    @Json(name = "timestamp") var timestamp:String?=null
    @Json(name = "metadata") var metadata:String?=null
}