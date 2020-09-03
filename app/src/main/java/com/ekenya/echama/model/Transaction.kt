package com.ekenya.echama.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import java.text.SimpleDateFormat
import java.util.*


@JsonClass(generateAdapter = true)
@Entity(tableName = "chama_transaction")
data class Transaction(@PrimaryKey var  transactionid: String) {
    var narration: String = ""
    var debitaccount: String = ""
    var creditaccount: String = ""
    var creditaccountname: String = ""
    var currency: String = "Ksh"    //Todo to be added by api
    var amount: Double = 0.00
    var contributionid: Int = 0
    var groupid: Int? = 0
    var groupname: String = ""
    var contributionname: String = ""
    var membername: String = ""
    var capturedby: String = ""
    var transactiondate: Long = 0L

    fun getFAmount():String{
       return String.format("%.2f",amount)
    }

    fun getFTrxDate():String{

        val simpleDateFormat = SimpleDateFormat("dd-MMM-yyyy | HH:mm a", Locale.ENGLISH)
        val dateStr: String = simpleDateFormat.format(transactiondate)
        return dateStr
    }

}