package com.ekenya.echama.ui.register

import com.squareup.moshi.Json


class RegisteredMemberCallBack (
    @Json(name="id") val id:String,
    @Json(name = "createdOn") val createdOn: String,
    @Json(name = "softDelete")val softDelete: String,
    @Json(name = "firstName")val firstName: String,
    @Json(name = "lastName") val lastName:String,
    @Json(name = "dateOfBirth") val dateOfBirth: String,
    @Json(name = "phoneNumber")val phoneNumber: String,
    @Json(name = "countyCode")val countyCode: String,
    @Json(name = "identification")val identification: String,
    @Json(name = "pendingRegistrationStatus")val pendingRegistrationStatus: String,
    @Json(name = "password") val password:String,
    @Json(name = "memberGroups") val memberGroups: String,
    @Json(name = "email")val email: String,
    @Json(name = "nationality")val nationality: String,
    @Json(name = "registeredMember")val registeredMember: String
    )