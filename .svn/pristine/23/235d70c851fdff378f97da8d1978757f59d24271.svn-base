package com.ekenya.echama.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.ekenya.echama.inc.AppInfo
import com.ekenya.echama.util.DataUtil
import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Types

@JsonClass(generateAdapter = true)
@Entity(tableName = "mygroup")
data class Group(
    @PrimaryKey   var groupid: Int
){
    var groupname: String = ""
    var groupsize: Int = 0
    var description: String? = ""
    var location: String = ""
    var activemembership: Boolean = false
    var title: String? = ""
    var createdby: String? = ""
    var createdon: String? = ""
    var size: Int? = 0
    var totalinvites: Int? = 0
    var walletbalance: Double? = 0.00
    var totalcontributions: Double? = 0.00
    var currency: String = "Ksh"
    var groupfilesJson: String? = ""
        get(){
            val type = Types.newParameterizedType(MutableList::class.java, Gfiles::class.java)
            val adapterRegion: JsonAdapter<List<Gfiles>> = AppInfo.moshi.adapter(type)
            return adapterRegion.toJson(groupfiles)!!
        }
    @Ignore
    var groupfiles: List<Gfiles>? = null

    var invitesPhonenumbers: String = ""
          get(){
              val type = Types.newParameterizedType(MutableList::class.java, String::class.java)
              val adapterRegion: JsonAdapter<List<String>> = AppInfo.moshi.adapter(type)
              return adapterRegion.toJson(invitesPhoneNoAr)!!
          }
    @Ignore
    @Json(name = "invites_phonenumbers")
    var invitesPhoneNoAr: List<String>? = null


    fun getFormattedCurrency():String{
        if(currency.isEmpty()){
            return "Ksh"
        }
        return currency
    }
    fun getFWalletBalance():String{
        return String.format("${getFormattedCurrency()} %.2f",walletbalance)
    }
    fun getFTotalContributions():String{
        return String.format("${getFormattedCurrency()} %.2f",totalcontributions)

    }


    fun fromJson(groupJson:String):Group {
        val adapterRegion: JsonAdapter<Group> = AppInfo.moshi.adapter(Group::class.java)
        adapterRegion.fromJson(groupJson)?.let {
            return it
        }
        return Group(0)
    }

    fun toJson():String{
        val adapterRegion: JsonAdapter<Group> = AppInfo.moshi.adapter(Group::class.java)
        return adapterRegion.toJson(this)
    }



}
class Gfiles(var filename:String)


@JsonClass(generateAdapter = true)
@Entity(tableName = "group_account")
data class GroupAccount(
    @PrimaryKey   var accountid: Int
){
       var accountname: String = ""
       var accountbalance: Double = 0.00

       var  accountdetailsJson: String? = ""
             get() {
                 return accountdetails?.toJson()
             }
      @Ignore var accountdetails: GroupAccountDetails?  = null
       var accounttypeid: Int = 0
       var groupid: Int = 0

    @Ignore
    @Transient
    var  accountTypeName: String? = ""

    fun getNameNamount():String{
       return accountname +"-"+DataUtil.gcurrency +" "+ accountbalance
    }
    fun getFBalance():String{
        return  String.format(" %.2f ${DataUtil.gcurrency}",accountbalance)
    }

    override fun toString(): String {
        return getNameNamount() + accounttypeid
    }
    fun toJson():String{
        val adapterRegion: JsonAdapter<GroupAccount> = AppInfo.moshi.adapter(GroupAccount::class.java)
        return adapterRegion.toJson(this)
    }

}
@JsonClass(generateAdapter = true)
data class GroupAccountDetails(
      @Json(name = "account_number") var accountNumber : String
){
    @Json(name = "sacco_name") var saccoName: String? = ""
    @Json(name = "branch") var branch: String? = ""
    @Json(name = "bank_name") var bankName: String? = ""
    @Json(name = "mno") var mnoProvider: String? = ""
    @Json(name = "description") var description: String? = ""
    @Json(name = "managedby") var managedby: String? = ""

    fun getNameNBranch():String{
        if(!saccoName.isNullOrEmpty()){
            return saccoName.toString() +"-"+branch
        }
        if(!bankName.isNullOrEmpty()){
            return bankName.toString()+"-"+branch
        }
        if(!managedby.isNullOrEmpty()){
            return managedby.toString()
        }
        return ""
    }
    fun getNameNAcc():String{
        if(!saccoName.isNullOrEmpty()){
            return saccoName.toString() +"-"+accountNumber
        }
        if(!bankName.isNullOrEmpty()){
            return bankName.toString()+"-"+accountNumber
        }
        return "Oops no name"
    }
    fun getName():String{
        if(!saccoName.isNullOrEmpty()){
            return saccoName.toString()
        }
        if(!bankName.isNullOrEmpty()){
            return bankName.toString()
        }
        return "Oops no name"
    }

    fun toJson():String{
        val adapterRegion: JsonAdapter<GroupAccountDetails> = AppInfo.moshi.adapter(GroupAccountDetails::class.java)
        return adapterRegion.toJson(this)
    }

}