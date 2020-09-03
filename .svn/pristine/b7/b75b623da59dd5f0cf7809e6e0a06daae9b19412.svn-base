package com.ekenya.echama.responseCalback


import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import org.json.JSONObject
import org.json.JSONStringer

@JsonClass(generateAdapter = true)
data class ResponseWrapper(
    @Json(name = "status") var status: String?
) {
    @Json(name = "data") var data:Any? = null
    @Json(name = "metadata") var metadata:Any? = null
    @Json(name = "timestamp") var timestamp:String? = null
    @Json(name = "message") var message:String? = null
    @Json(name = "error") var error:String? = null
    @Json(name = "error_description") var error_description:String? = null



    fun getApiResponse():String{
        return status.toString()
    }

    //    fun listData(): Any {
    //        return  data
    //    }
}