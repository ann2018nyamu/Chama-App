package com.ekenya.echama.ui.register

import com.ekenya.echama.inc.AppInfo.Companion.moshi
import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types


class RegionCallback (
    @Json(name = "id") val id:String,
    @Json(name = "createdOn") val createdOn: String,
    @Json(name = "softDelete")val softDelete: String,
    @Json(name = "name")val name: String,
    @Json(name = "departments") val departments: List<Department>
){

    inner class Department(
        @Json(name = "id") var id: Int,
        @Json(name = "createdOn") val createdOn: String,
        @Json(name = "softDelete")val softDelete: String?,
        @Json(name = "name") val name: String
    )

    fun getDepartment() :List<Department>{
       // val gson = GsonBuilder().create()
       // val Model= gson.fromJson(Department(),List<Department>()).toList()
        return departments

//        val type = Types.newParameterizedType(
//                            MutableList::class.java,
//            Department::class.java
//                        )
//                        val adapter: JsonAdapter<List<Department>> = moshi.adapter(type)
//                      return   adapter.fromJson("")!!
//
//        val depList = Gson().fromJson<List<Department>>(departments,object : TypeToken<List<Department?>?>() {}.type)
//        return depList
    }
}