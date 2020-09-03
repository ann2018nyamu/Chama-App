package com.ekenya.echama.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.ekenya.echama.inc.AppInfo
import com.ekenya.echama.util.DataUtil
import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "contribution")
data class Contribution(@PrimaryKey  var id:Int=0) {
    var name:String = ""
    var startdate:String = ""
    var enddate:String = ""
    var contributionDetailsJson:String? = null
         get() {
           return  contributiondetails?.toJson()
         }
   @Ignore var contributiondetails:ContributionDetails? = null
    var contributiontypeid: Int = 0
    var contributiontypename:String = ""
    var scheduletypeid:Int = 0
    var scheduletypename:String = ""
    var amounttypeid:Int = 0
    var amounttypename:String = ""
    var groupid:Int = 0
    var groupname:String = ""
    var debitaccountid:Int = 0
    var amountcontributed:Double = 0.00
    var active:Boolean = false
    var createdby:String = ""

    fun getFContributedAmount():String{

        return String.format("${DataUtil.gcurrency} %.2f",amountcontributed)
    }
    //Todo compute duedate
    fun getDueDate():String{
        if(enddate.contentEquals("recurring contribution")){
           return "recurring"
        }
        return enddate
    }

    override fun toString(): String {
        return "${name}(${getFContributedAmount()})"
    }

    fun fromJson(groupJson:String):Contribution {
        val adapterRegion: JsonAdapter<Contribution> = AppInfo.moshi.adapter(Contribution::class.java)
        adapterRegion.fromJson(groupJson)?.let {
            return it
        }
        return Contribution(0)
    }

    fun toJson():String{
        val adapterRegion: JsonAdapter<Contribution> = AppInfo.moshi.adapter(Contribution::class.java)
        return adapterRegion.toJson(this)
    }
}
@JsonClass(generateAdapter = true)
@Entity(tableName = "contribution_type")
class ContributionType(
    @PrimaryKey @Json(name = "id") var id:Int,
    @Json(name = "createdOn") var createdOn: String,
    @Json(name = "softDelete") var softDelete: Boolean,
    @Json(name = "name") var name: String
)

@JsonClass(generateAdapter = true)
class ContributionDetails {
    var amount:Double = 0.00
    var penalties:Penalty? = null
    var memberamount:Double = 0.00
    var reminder:Reminder? = null
    var endDate:String? = ""

    fun toJson():String{
        val adapterRegion: JsonAdapter<ContributionDetails> = AppInfo.moshi.adapter(ContributionDetails::class.java)
        return adapterRegion.toJson(this)
    }
}

@JsonClass(generateAdapter = true)
class Reminder {
    var note:String = ""
    var applied:Boolean = false
    var startdate:CroneJob? = null
    var enddate:CroneJob? = null
    var frequency:CroneJob? = null

    fun toJson():String{
        val adapterRegion: JsonAdapter<Reminder> = AppInfo.moshi.adapter(Reminder::class.java)
        return adapterRegion.toJson(this)
    }
    fun fromJson(groupJson:String):Reminder {
        val adapterRegion: JsonAdapter<Reminder> = AppInfo.moshi.adapter(Reminder::class.java)
        adapterRegion.fromJson(groupJson)?.let {
            return it
        }
        return Reminder()
    }
}

@JsonClass(generateAdapter = true)
class Penalty {
    var amount:Double = 0.00
    var applied:Boolean = false
    var startdate:CroneJob? = null
    var frequency:CroneJob? = null
    var penaltytype:String = ""
}
@JsonClass(generateAdapter = true)
class CroneJob{
    var dayofmonth = "*"
    var dayofweek = "*"
    var hour = "12"
    var month = "*"
    var minute= "00"
}