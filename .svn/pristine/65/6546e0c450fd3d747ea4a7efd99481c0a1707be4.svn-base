package com.ekenya.echama.model

import androidx.room.Ignore
import com.ekenya.echama.inc.AppInfo
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Types
import org.json.JSONObject
import timber.log.Timber
import java.lang.reflect.Type

@JsonClass(generateAdapter = true)
 class Permission(var roleJson:Any? ) {
    @Ignore var roleH:Map<String,Role> = HashMap()
    @Ignore var roleList:ArrayList<Role> = ArrayList()


     var groupid:Int = 0

     init {

          val type: Type = Types.newParameterizedType(
               Map::class.java,String::class.java, Any::class.java
          )

          val adapters: JsonAdapter<Map<String, ArrayList<String>>> = AppInfo.moshi.adapter(type)

         this.roleJson?.let{


             var map = adapters.fromJsonValue(this.roleJson)!!
             Timber.v(map.toString())
             Timber.v(map.size.toString())
             var roleH = HashMap<String,Role>()
             this.roleList.clear()
             try {
                 for ((key, value) in map) {
                    var values = value.toMutableList().toString()
                     var role = Role(key, value)
                     Role(key,value)
                     role.roleName = key
                     if (values.contains("cancreate")) {
                         role.cancreate = true
                     }
                     if (values.contains("candelete")) {
                         role.candelete = true
                     }
                     if (values.contains("canedit")) {
                         role.canedit = true
                     }
                     if (values.contains("canview")) {
                         role.canview = true
                     }
                     roleH[key] = role
                       this.roleList.add(role)
                 }
             }catch (e:Exception){
                 Timber.v(e.message)
             }
             this.roleH = roleH


         }

     }
    fun toPermissionJson():Any{
        var json = HashMap<String,Any>()
        for(role in roleList){
            json.put(role.roleName,  role.getChoosenRole())
        }
        return json
    }
    fun canPerform(action:String):Role{
        roleH[action]?.let {
                return  it
        }
//        if(roleH.containsKey(action)){
//            return roleH[action] ?: error("")
//        }

        return Role()
    }



}
class Role(var key: String? = "", var value: ArrayList<String>? = null) {
    var roleName:String = ""
     var cancreate:Boolean = false
     var canedit:Boolean = false
     var canview:Boolean = false
     var candelete:Boolean = false
    init {
        key?.let {
            this.roleName = it
        }
        value?.let {

            if (it.contains("cancreate")) {
                this.cancreate = true
            }
            if (it.contains("candelete")) {
                this.candelete = true
            }
            if (it.contains("canedit")) {
                this.canedit = true
            }
            if (it.contains("canview")) {
                this.canview = true
            }
        }

    }


    fun getChoosenRole():ArrayList<String>{
        var crule = ArrayList<String>()
        if (cancreate) {
            crule.add("cancreate")
        }
        if (candelete) {
            crule.add("candelete")
        }
        if (canedit) {
            crule.add("canedit")
        }
        if (canview) {
            crule.add("canview")
        }
        return crule
    }



    fun getFRoleName(): String {
        return when (roleName) {
            "loans" -> {
                return "Loans"
            }
            "invites" -> {
                return "Invites"
            }
            "members" -> {
                return "Members"
            }
           "dueloans" -> {
                return "Due Loans"
            }
           "documents" -> {
                return "Documents"
            }
           "loanpayment" -> {
                return "Loan Payment"
            }
           "loanproduct" -> {
                return "Loan Products"
            }
           "contribution" -> {
                return "Contribution"
            }
            "groupaccount" -> {
                return "Group Account"
            }
           "groupdetails" -> {
                return "Group details"
            }
           "contributionpayment" -> {
                return "Contribution Payment"
            }
           "loanspendingapproval" -> {
                return "Loan Pending Approval"
            }
           "contributionwithdrawal" -> {
                return "Contribution Withdrawals"
            }
            "loanpaymentspendingapproval" -> {
                return "Loan Payment Approval"
            }
            else -> {
                return "Contact support"

            }
        }
    }

}


