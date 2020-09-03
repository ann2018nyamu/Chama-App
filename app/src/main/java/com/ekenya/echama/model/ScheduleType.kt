package com.ekenya.echama.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "schedule_type")
class ScheduleType (
    @PrimaryKey @Json(name = "id") var id:Int,
    @Json(name = "createdOn") var createdOn: String,
    @Json(name = "softDelete")var softDelete: Boolean,
    @Json(name = "name")var name: String
    )