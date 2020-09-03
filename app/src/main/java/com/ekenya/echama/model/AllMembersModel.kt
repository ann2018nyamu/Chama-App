package com.ekenya.echama.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass



class AllMembersModel {
    inner class Content(
        @Json(name = "member") var member: Member,//inviteAccepted
        @Json(name = "totalContribution") var totalContribution: Int
    ) {

    }
        inner class Member(
            @Json(name = "id") var id: Int,
            @Json(name = "createdOn") val createdOn: String,
            @Json(name = "softDelete") val softDelete: String?,
            @Json(name = "firstName") val firstName: String,//country
            @Json(name = "lastName") var lastName: String?,
            @Json(name = "dateOfBirth") val dateOfBirth: String,
            @Json(name = "phoneNumber") val phoneNumber: String?,
            @Json(name = "countyCode") val countyCode: String?,
            @Json(name = "identification") val identification: String?,
            @Json(name = "password") val password: String,//country
            @Json(name = "pendingRegistrationStatus") var pendingRegistrationStatus: Boolean?,
            @Json(name = "memberGroups") val memberGroups: AllGroupsModel.MemberGroups?,
            @Json(name = "email") val email: String?,
            @Json(name = "registeredMember") val registeredMember: Boolean?,
            @Json(name = "nationality") val nationality: String?,
            var totalContribution:Int?
        )

        inner class TotalContribution(
            @Json(name = "totalContribution") var totalContribution: Int
        )

}

class OfficialMemberRole(
    val memberId:String
)