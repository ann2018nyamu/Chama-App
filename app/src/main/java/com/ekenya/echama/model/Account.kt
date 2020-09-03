package com.ekenya.echama.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "account_type")
data class AccountType(
    @PrimaryKey   val id: Int,
    val name: String,
    val prefix: String
)


