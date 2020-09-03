package com.ekenya.echama.util

import android.content.Context
import android.os.Build
import android.provider.Settings
import com.ekenya.echama.model.Group
import com.ekenya.echama.model.Member
import com.ekenya.echama.model.Permission
import com.ekenya.echama.ui.register.RegionCallback
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import timber.log.Timber
import java.io.File
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class DataUtil{

    companion object{
        var gcurrency: String = "Ksh"
        lateinit var dept:ArrayList<RegionCallback.Department>
        var groupTypeId:String=""
        var contributionTypeId = ""
        var scheduleId = ""
        var selectedContacts:List<Member> = ArrayList()
        var pass:String = ""
        var userGrpPermission:Permission = Permission(null)
        var authToken:String = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiY2hhbWEiXSwidXNlcl9uYW1lIjoiMjU0NzE1NzAyODg3Iiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImV4cCI6MTU4ODQ0OTM1NiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImp0aSI6ImU4YTg0MDM0LWEyZmQtNGQ3NC1iNmZjLTFlNzVkNDFmMDFjOCIsImNsaWVudF9pZCI6ImNoYW1hX2NsaWVudGlkIiwic3RhdHVzIjoic3VjY2VzcyJ9.kch2zjNH_FsGOqxJ3dMDi7qJ9q61OyPi0ibEn5kuU6qEgc8ew_Ifb4yzo7XCQ3mN2D9lAhe6xQAUKCE2N_ufrTZaxdZ_tezJ-hxqDxyN5CgPAOi_1QHAee1VHznuZzM2kCSEsLUH80SmBNXVeh01_5vXK3p_40rPQ02XVj2mHhPWwaWVDJ6L68CQqwORbktP1aGUlWhukfiAf9S487yzDbG1Byz5hkvdmpsLG3c_pWHeO4xehNTHXYzKkzsf-CULrhj2ZaV4om1PuUVYiPgZH_Tp_V0VtyWE-7pdJHwhxvybtSW1uwgAQu9xM_A7U4JwsauTeGq7KcSfE8isUyv1Fw"

        var currentGroup: Group = Group(0)
        var grpFilesAr : ArrayList<File> =  ArrayList()
        var grpFilesMpAr : ArrayList<MultipartBody.Part> =  ArrayList()
        var selectedGrpId:Int = 1 //Todo change to 0 when live

        fun getFileMultipart():ArrayList<MultipartBody.Part>{
            var grpFilesMpAr : ArrayList<MultipartBody.Part> =  ArrayList()
            if(grpFilesAr.isEmpty()){
                Timber.v("No file available")
                return grpFilesMpAr
            }
            for(file in grpFilesAr) {
                val requestBody1: RequestBody = file.asRequestBody()
                val fileToUpload: MultipartBody.Part =
                    MultipartBody.Part.createFormData("files", file.name, requestBody1)
                grpFilesMpAr.add(fileToUpload)
            }
            return grpFilesMpAr
        }

        fun getHashToken():HashMap<String,String>{
            var hashToken = HashMap<String,String>()//authtoken
          //  hashToken.put("Authorization","Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI3Mjg4NDQwMzQiLCJleHAiOjE1Nzg0MjYzMDQsImlhdCI6MTU3ODQwODMwNH0.82MMK6r-gtxcq2TIEBBtoKC8RQJlErchWv3hOah4D6Me1D5ep2VuOnMa8db7GfNmefI-e9bQTV5DN8zvV0-fPA")
            hashToken.put("Authorization", authToken)
            return hashToken
        }
        fun isEmulator(context: Context): Boolean {
            val androidId: String =
                Settings.Secure.getString(context.contentResolver, "android_id")
            return "sdk" == Build.PRODUCT || "google_sdk" == Build.PRODUCT || androidId == null
        }

        fun isRooted(context: Context): Boolean {
            val isEmulator: Boolean = isEmulator(context)
            val buildTags = Build.TAGS
            return if (!isEmulator && buildTags != null && buildTags.contains("test-keys")) {
                true
            } else {
                var file = File("/system/app/Superuser.apk")
                if (file.exists()) {
                    true
                } else {
                    file = File("/system/xbin/su")
                    !isEmulator && file.exists()
                }
            }
        }

        /**
         * hash pin
         *
         * @param toncrypt the toncrypt
         * @return the string
         */
        fun getHashedPin256(toncrypt: String): String? {
            //String password="4544545454trerrt8";
            var digest: MessageDigest? = null
            val hash: String
            try {
                digest = MessageDigest.getInstance("SHA-256")
                digest.update(toncrypt.toByteArray())
                hash = bytesToHexString(digest.digest())
                return hash
            } catch (e1: NoSuchAlgorithmException) {
            }
            return ""
        }
        fun bytesToHexString(bytes: ByteArray): String {
            val sb = StringBuffer()
            for (i in bytes.indices) {
                val hex = Integer.toHexString(0xFF and bytes[i].toInt())
                if (hex.length == 1) {
                    sb.append('0')
                }
                sb.append(hex)
            }
            return sb.toString()
        }


    }
}