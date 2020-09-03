package com.ekenya.echama.model

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.ekenya.echama.inc.AppInfo
import com.ekenya.echama.util.DataUtil
import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

@JsonClass(generateAdapter = true)
@Entity(tableName = "loan_product")
data class LoanProduct (@PrimaryKey  @Json(name = "productid") var loanProductId: Int = 0){
    @Json(name = "productname") var loanProductName: String? = ""
    @Json(name = "description") var loanProductDesc: String? = ""
    @Json(name = "interesttype") var interestType: String = ""
    @Json(name = "interestvalue") var interestRate: Double = 0.00
    @Json(name = "paymentperiod") var period: Double = 0.0
    @Json(name = "min_principal") var minimumAmount: Double = 0.00
    @Json(name = "max_principal")  var maximumAmount: Double = 0.00
      var paymentperiodtype: String = ""
      var currency: String = "Ksh"
      var groupid: Int = 0

 fun getFMinumAmount():String{
     return String.format("${currency} %.2f",minimumAmount)
 }

 fun getFMaximumAmount():String{
     return String.format("${currency} %.2f",maximumAmount)
 }
    fun getInterestRateValue():String{
        if(interestType.toLowerCase().contains("interest")){
           return  String.format("%.2f ",interestRate)+"%"
        }
        return interestRate.toString()
    }


    fun fromJson(groupJson:String):LoanProduct {
        val adapterRegion: JsonAdapter<LoanProduct> = AppInfo.moshi.adapter(LoanProduct::class.java)
        adapterRegion.fromJson(groupJson)?.let {
            return it
        }
        return LoanProduct()
    }

    fun toJson():String{
        val adapterRegion: JsonAdapter<LoanProduct> = AppInfo.moshi.adapter(LoanProduct::class.java)
        return adapterRegion.toJson(this)
    }

}

@JsonClass(generateAdapter = true)
data class Loan (@PrimaryKey  @Json(name = "loanapplicationid") var loanId: Int = 0){
     @Json(name = "membername")  var memberName: String? = ""
    @Json(name = "memberphonenumber") var memberPhone: String? = ""
   @Json(name = "loanproductname")  var loanProductName: String? = ""
    var disbursedDate: String? = ""
    var completedDate: String? = ""
    var reminderStr: String? = ""
        set(value) { field = reminder?.toJson() }

    var amount: Double = 0.00
    var balanceAmount: Double = 0.00
    var appliedon: Long = 0L
    var groupid: Int = 0
    var unpaidloans: Int = 0
    var loanproductid: Int = 0
   @Ignore var reminder: Reminder? = null




   fun getFAppliedonDate():String{
       val simpleDateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)
       val dateStr: String = simpleDateFormat.format(appliedon)
       return dateStr
   }
    fun getReminderObj():Reminder {
        if(reminder == null){
            return Reminder().fromJson(reminderStr.toString())
        }
       return reminder as Reminder
    }

    fun getFAmount():String{
        return String.format(" ${DataUtil.gcurrency} %.2f",amount)
    }
    fun getFbalanceAmount():String{
        return String.format(" ${DataUtil.gcurrency} %.2f",balanceAmount)
    }
    fun fromJson(groupJson:String):Loan {
        val adapterRegion: JsonAdapter<Loan> = AppInfo.moshi.adapter(Loan::class.java)

            adapterRegion.fromJson(groupJson)?.let {
                return it
            }


        return Loan()
    }

    fun toJson():String{
        val adapterRegion: JsonAdapter<Loan> = AppInfo.moshi.adapter(Loan::class.java)
        return adapterRegion.toJson(this)
    }

}
@JsonClass(generateAdapter = true)
@Entity(tableName = "loan")
data class LoanApproved (@PrimaryKey  @Json(name = "loanid") var loanId: Int = 0) {
    @Json(name = "principal") var principalAmount: Double = 0.0
    @Json(name = "interest") var interestRate: Double = 0.0
    @Json(name = "dueamount") var balanceAmount: Double = 0.0
    @Json(name = "duedate") var dueDate: Long = 0L
    @Json(name = "recipient") var memberName: String = ""
    @Json(name = "recipientsnumber") var memberPhone: String = ""
    @Json(name = "groupname") var groupName: String = ""
    @Json(name = "contributionname") var contributionName: String = ""
    var groupid: Int = 0
    var contributionid: Int = 0


    fun getFBalanceAmount():String{
        return String.format(" ${DataUtil.gcurrency} %.2f",balanceAmount)
    }
    fun getFPrincipalAmount():String{
        return String.format(" ${DataUtil.gcurrency} %.2f",principalAmount)
    }
    fun getFDueDate():String{

        val simpleDateFormat = SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.ENGLISH)
        val dateStr: String = simpleDateFormat.format(dueDate)
        return dateStr
    }
    fun fromJson(groupJson:String):LoanApproved {
        val adapterRegion: JsonAdapter<LoanApproved> = AppInfo.moshi.adapter(LoanApproved::class.java)

        adapterRegion.fromJson(groupJson)?.let {
            return it
        }
        return LoanApproved()
    }

    fun toJson():String{
        val adapterRegion: JsonAdapter<LoanApproved> = AppInfo.moshi.adapter(LoanApproved::class.java)
        return adapterRegion.toJson(this)
    }

}

@JsonClass(generateAdapter = true)
data class LoanPayment (@PrimaryKey  @Json(name = "receiptnumber") var receiptnumber: String = ""){
    @Json(name = "amount") var paymentAmount: Double = 0.0
    @Json(name = "oldamount") var previousAmount: Double = 0.0
    @Json(name = "newamount") var balanceAmount: Double = 0.0
    @Json(name = "loandisbursedid") var loanDisbursedId: Int = 0
    @Json(name = "loanproductname") var loanProductName: String = ""
    @Json(name = "membername") var memberName: String = ""
    @Json(name = "memberphonenumber") var memberPhoneNumber: String = ""

    fun getFPaidAmount():String{
        return String.format(" ${DataUtil.gcurrency} %.2f",paymentAmount)
    }
    fun getFPreviousAmount():String{
        return String.format(" ${DataUtil.gcurrency} %.2f",previousAmount)
    }
    fun getFBalanceAmount():String{
        return String.format(" ${DataUtil.gcurrency} %.2f",balanceAmount)
    }

    fun fromJson(groupJson:String):LoanPayment {
        val adapterRegion: JsonAdapter<LoanPayment> = AppInfo.moshi.adapter(LoanPayment::class.java)

            adapterRegion.fromJson(groupJson)?.let {
                return it
            }

        return LoanPayment()
    }

    fun toJson():String{
        val adapterRegion: JsonAdapter<LoanPayment> = AppInfo.moshi.adapter(LoanPayment::class.java)
        return adapterRegion.toJson(this)
    }
}

