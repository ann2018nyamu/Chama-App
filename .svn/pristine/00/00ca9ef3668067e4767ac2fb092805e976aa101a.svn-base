package com.ekenya.echama.model

import androidx.room.Entity
import com.ekenya.echama.inc.AppInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "members",primaryKeys = ["id", "groupid"])
class Member (
    var name:String,
    @Json(name = "phonenumber") var phoneNumber:String

){
    var id: Int = 0
    var groupid: Int = 0
    @Json(name = "title") var role:String? = "Member"
    var email: String = ""
    var groupname: String = ""
    var activemembership: Boolean = false
    var createdon: String = ""

    fun getFPhoneNumber():String{
        if(phoneNumber.startsWith("07")){
            return phoneNumber.replaceFirst("07","2547").replace(" ","")
        }
        return phoneNumber.replace(" ","")

    }
    fun fromJson(groupJson:String):Member {
        val adapterRegion: JsonAdapter<Member> = AppInfo.moshi.adapter(Member::class.java)
        adapterRegion.fromJson(groupJson)?.let {
            return it
        }
        return Member("","")
    }

    fun toJson():String{
        val adapterRegion: JsonAdapter<Member> = AppInfo.moshi.adapter(Member::class.java)
        return adapterRegion.toJson(this)
    }
}

