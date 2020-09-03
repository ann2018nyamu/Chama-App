package com.ekenya.echama.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "country")
data class Country(
    @PrimaryKey  @Json(name = "code") val countryCode: String,
    @Json(name = "nationality")val nationality: String,
    @Json(name = "dialcode")val dialCode: String,
    @Json(name = "name") val countryName: String,
    @Json(name = "language") val countryLanguage: String
)