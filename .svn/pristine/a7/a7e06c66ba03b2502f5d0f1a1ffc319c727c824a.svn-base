package com.ekenya.echama.model

import com.ekenya.echama.inc.AppInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types


class AllGroupsModel(
    @Json(name = "content") val content:String,
    @Json(name = "pageable") val pageable: String,
    @Json(name = "sort")val sort: String,
    @Json(name = "totalElements")val totalElements: Int,
    @Json(name = "totalPages") val totalPages: Int,
    @Json(name = "last")val last: Boolean,
    @Json(name = "first") val first: Boolean,
    @Json(name = "numberOfElements")val numberOfElements: Int,
    @Json(name = "size") val size: Int,
    @Json(name = "number") val number: Int,
    @Json(name = "empty")val empty: Boolean

)
{
    inner class Content(
        @Json(name = "memberGroups") var memberGroups: List<MemberGroups>,//inviteAccepted
        @Json(name = "inviteAccepted") var inviteAccepted: Boolean
    )
    {

        fun getGroupDetails(): List<MemberGroups> {

            //            val type = Types.newParameterizedType(
            //                MutableList::class.java,
            //                MemberGroups::class.java
            //            )
            //            val adapter: JsonAdapter<List<MemberGroups>> = AppInfo.moshi.adapter(type)
            //          return   adapter.fromJson("")!!

            return  memberGroups
        }
    }
    inner class MemberGroups(
        @Json(name = "id") var id: Int,
        @Json(name = "createdOn") val createdOn: String,
        @Json(name = "softDelete")val softDelete: String?,
        @Json(name = "name") val name: String,//country
        @Json(name = "country") val country: String,
        @Json(name = "city") val city: String,
        @Json(name = "creator") val creator: String
    )

//    inner class MyMemberGroups(
//        @Json(name = "groupid") var groupId: Int,
//        @Json(name = "groupname") val groupName: String,
//        @Json(name = "location")val location: String,
//        @Json(name = "groupsize") val groupSize: String,
//        @Json(name = "activemembership") val activeMembership: Boolean,
//        @Json(name = "title") val title: String?
//    )



}