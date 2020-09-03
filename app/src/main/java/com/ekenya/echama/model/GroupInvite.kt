package com.ekenya.echama.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "groupinvite")
data class GroupInvite(
    @PrimaryKey @Json(name = "inviteid") var id: Int

){
    @Json(name = "createdon") var createdOn: String = ""
    @Json(name = "groupname")var groupname: String = ""
    @Json(name = "groupimgurl") var groupimgurl: String? = ""
}
//class GroupInvites(
//    val id: Int,
//    val name:String,
//    val invitee:String
//)