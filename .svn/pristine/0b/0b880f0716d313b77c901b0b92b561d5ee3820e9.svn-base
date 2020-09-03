package com.ekenya.echama.util

import android.content.Context
import com.ekenya.echama.R

class SharedPrefenceUtil{

    fun getUserId(context: Context):Int?{
        val sharedPref=context.getSharedPreferences(preferenceName,Context.MODE_PRIVATE)
        return sharedPref.getInt("userId",0)

    }
    fun setUserId(userId:Int,context: Context) {
        val sharedPref =
            context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putInt("userId", userId)
            apply()
        }
    }


    fun getGroupId(context: Context):Int?{
        val sharedPref=context.getSharedPreferences(preferenceName,Context.MODE_PRIVATE)
        return sharedPref.getInt("groupid",0)

    }
    fun setGroupId(groupId:Int,context: Context) {
        val sharedPref =
            context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putInt("groupid", groupId)
            apply()
        }
    }


        fun getUserName(context: Context): String? {
            val sharedPref = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)
            return sharedPref.getString("username", "no")

        }

        fun setUserName(username: String, context: Context) {
            val sharedPref =
                context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE) ?: return
            with(sharedPref.edit()) {
                putString("username", username)
                apply()
            }

        }


    /**
     * method that return token
     * @param context
     * @return authtoken string
     */
    fun getAuthToken(context: Context):String?{
        val sharedPref = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)
        return sharedPref.getString(authToken, "")
    }

        /**
         * method to set token
         * @param context
         * @param token
         */
        fun setAuthToken(authToken:String,context:Context){
            val sharedPref =
                context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE) ?: return
            with(sharedPref.edit()) {
                putString(authToken, authToken)
                apply()
            }
        }




    fun getPhoneNumber(context: Context):String? {
        val sharedPref = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)
        return sharedPref.getString("phonenumber", "")

    }

    fun setPhoneNumber(phoneNumber:String, context: Context) {
        val sharedPref =
            context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString("phonenumber", phoneNumber)
            apply()
        }
    }


    fun getOtp(context: Context):String? {
        val sharedPref = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)
        return sharedPref.getString("otp", "")

    }

    fun setOtp(otp:String, context: Context) {
        val sharedPref =
            context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString("otp", otp)
            apply()
        }
    }



    companion object{
        val userName:String=""
        val preferenceName = "PrefName"
        val authToken = "token"
        val phoneNumber = "phonenumber"
    }
}