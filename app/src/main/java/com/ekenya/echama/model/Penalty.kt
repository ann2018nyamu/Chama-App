package com.ekenya.echama.model


import com.ekenya.echama.inc.AppInfo
import com.ekenya.echama.util.DataUtil
import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GroupPenalty(
    @Json(name = "membername") var memberName: String
){
    @Json(name = "amount")var penaltyAmount: Double? = 0.00
    @Json(name = "name") var contributionName: String? = ""
    @Json(name = "memberphone") var memberphone: String? = ""
    @Json(name = "memberPicurl") var memberPicUrl: String? = "https://via.placeholder.com/150.jpg"

    fun getFPenaltyAmount():String{
        return String.format("%.2f",penaltyAmount)
    }

    fun fromJson(groupJson:String):GroupPenalty {
        val adapterRegion: JsonAdapter<GroupPenalty> = AppInfo.moshi.adapter(GroupPenalty::class.java)
        adapterRegion.fromJson(groupJson)?.let {
            return it
        }
        return GroupPenalty("")
    }

    fun toJson():String{
        val adapterRegion: JsonAdapter<GroupPenalty> = AppInfo.moshi.adapter(GroupPenalty::class.java)
        return adapterRegion.toJson(this)
    }

}
