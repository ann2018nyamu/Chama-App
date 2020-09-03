package com.ekenya.echama.repository

import com.ekenya.echama.inc.AppInfo
import com.ekenya.echama.responseCalback.CountryResponse
import com.squareup.moshi.JsonClass
import timber.log.Timber


class MyApiResponse(
    val code: Int = 400,
    val requestName: String = "",
    val message: String = "Error response $code",
    val responseObj: Any? = null
){

}
class ResponseObj<T>(t: T) {
    var value = t
}

@JsonClass(generateAdapter = true)
 class MyError(jsonError:String? = ""){
    var error:String = ""
    var message:String? = ""
    var error_description:String = ""
    init {

        val myErrorAd = AppInfo.moshi.adapter(MyError::class.java).lenient()
        if(!jsonError.isNullOrBlank() ) {
            try {
                Timber.v(jsonError)
                val myError = myErrorAd.fromJson(jsonError)!!
                message = myError.message
                if (myError.error_description.isNotEmpty()) {
                    message = myError.error_description
                    if (
                        myError.error_description.contains("Access token expired")
                        || myError.error_description.contains("Full authentication")
                    ) {
                        message = "Access token expired"
                    }
                }else{

                    if(message.isNullOrEmpty()){
                        message = "Oops an error occurred"
                    }
                }

                error = myError.error

            }catch (e:Exception){
                Timber.v(e.message)
            }
        }

    }
    fun getFormattedError(): String{
        var msg = ""
        if(!error.isNullOrBlank() ) {
            msg += error
            msg += ": "
        }
         if(!message.isNullOrBlank() ) {
             msg += message
         }

        return msg
    }
}
