package com.ekenya.echama.responseCalback


import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import org.json.JSONObject
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType
@JsonClass(generateAdapter = true)
 class LoginWrapper  (
//    @Json(name ="access_token") var access_token: String,
    @Json(name ="access_token") var accessToken: String,
    @Json(name ="token_type") var tokenType: String,
    @Json(name ="refresh_token") var refreshToken: String,
    @Json(name ="error") var error: String?,
    @Json(name ="chama") var chama: String?,
    @Json(name ="jti") var jti: String,
    @Json(name ="expires_in") var expiresIn : String
  //  @Json(name ="status") var status: String
) {
    constructor() : this("","","","","","","") {

    }

    inner class ApiResponse(
        @Json(name ="status") val status:String,
        @Json(name ="message") val message:String
    )

//    fun listData(): Any {
//        return  accessToken
//    }
//
//    fun listData2(): Any {
//        return  accessToken
//    }

    fun listData3(): Any {
        return  refreshToken
    }

//    fun listData4(): Any {
//        return  scope
//    }

    fun listData5(): Any {
        return  expiresIn
    }

    fun listData6(): Any {
        return  jti
    }

}