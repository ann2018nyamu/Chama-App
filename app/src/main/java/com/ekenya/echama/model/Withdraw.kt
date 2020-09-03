package com.ekenya.echama.model

import com.ekenya.echama.util.DataUtil
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Withdraw( @Json(name = "requestid") var withdrawId:Int = 0) {
    var debitaccountname:String = ""
    var debitaccounttype:String = ""
    var creditaccount:String = ""
    var debitaccountid:Int = 0
    var amount:Double = 0.0
    var contributionid:Int = 0
    var capturedby:String = ""
    @Json(name = "withdrawal_narration") var narration:String = ""
    @Json(name = "withdrawalreason") var reason:String = ""

    fun getFAmount():String{
        return String.format(DataUtil.gcurrency+" %.2f",amount)
    }
}