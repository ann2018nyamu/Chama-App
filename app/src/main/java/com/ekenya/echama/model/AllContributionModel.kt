package com.ekenya.echama.model

import com.squareup.moshi.Json


class AllContributionModel(
    @Json(name = "id") var id: Int,
    @Json(name = "createdOn") val createdOn: String,
    @Json(name = "softDelete")val softDelete: String?,
    @Json(name = "name") val name: String,//targetAmount
    @Json(name = "targetAmount") var targetAmount: String,
    @Json(name = "amountPerMember") val amountPerMember: String,
    @Json(name = "startDate")val startDate: String?,
    @Json(name = "endDate") val endDate: String,
    @Json(name = "penalityAvailable") val penalityAvailable: Boolean,//targetAmount
    @Json(name = "remainderSet") var remainderSet: Boolean,
    @Json(name = "contributionType") val contributionType: ContributionType,
    @Json(name = "indivualContributionType")val indivualContributionType: IndivualContributionType?,
    @Json(name = "scheduleType") val scheduleType: ScheduleType
)
{


    inner class IndivualContributionType(
        @Json(name = "id") var id: Int,
        @Json(name = "createdOn") val createdOn: String,
        @Json(name = "softDelete")val softDelete: String?,
        @Json(name = "name") val name: String
    )
    inner class ScheduleType(
        @Json(name = "id") var id: Int,
        @Json(name = "createdOn") val createdOn: String,
        @Json(name = "softDelete")val softDelete: String?,
        @Json(name = "name") val name: String,
        @Json(name = "frequencyOfPayment") val frequencyOfPayment: Int
    )


}
