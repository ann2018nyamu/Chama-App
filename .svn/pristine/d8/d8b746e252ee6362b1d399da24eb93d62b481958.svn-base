package com.oscar.retrofitcertificate.retrofitehandler

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ekenya.echama.dao.CountryDao
import com.ekenya.echama.dao.UserDao
import com.ekenya.echama.inc.AppInfo
import com.ekenya.echama.inc.ChamaRD
import com.ekenya.echama.model.Country
import com.ekenya.echama.network.RestClient
import com.ekenya.echama.repository.MyApiResponse
import com.ekenya.echama.repository.MyError
import com.ekenya.echama.responseCalback.LoginWrapper
import com.ekenya.echama.responseCalback.ResponseWrapper
import com.ekenya.echama.util.toJson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import timber.log.Timber


interface Repository {
    // fun getCountries():LiveData<List<Country>>
    //suspend fun getNationalities(): List<String>
     //fun getNationalitiesList():LiveData<List<Country>>
   // suspend fun getConfigRegionList():ResponseWrapper
    // fun registerMember(json:HashMap<String,String>)
    //suspend fun updatePassword(json: HashMap<String,String>):ResponseWrapper
   // suspend fun userLogin(username:String,pass:String,grant:String):LoginWrapper
    //suspend fun getGroupType():ResponseWrapper
    // fun verifyOtp(json: HashMap<String,String>)
    }

class RepoImpl(val scope: CoroutineScope) : Repository {
    var countryDao: CountryDao = ChamaRD.getDatabase(AppInfo.appC,scope).countryDao()
    var userDao: UserDao = ChamaRD.getDatabase(AppInfo.appC,scope).userDao()
    var  myApiResponse: MutableLiveData<MyApiResponse> = MutableLiveData<MyApiResponse>()
    var  successResponse: MutableLiveData<MyApiResponse> = MutableLiveData<MyApiResponse>()
    var   myError = MyError()

    lateinit var hashMap:HashMap<String,String>


//        override suspend fun getGroupType(): ResponseWrapper {
//            return RestClient.apiService.getGroupType(HashMap(),"".toRequestBody())
//        }



//        override suspend fun getCountries(): ResponseWrapper {
//            return RestClient.apiService.getNationalities()
//        }

//        override suspend fun getNationalities(): List<String> {
//            return RestClient.apiService.gettodoLst( HashMap(), "".toRequestBody())
//        }

//        override  fun getCountries(): LiveData<List<Country>> {
//            var countryListLD = countryDao.getCountries()
//            Timber.v("getCountries size %s",countryListLD.value?.size)
//           // if(countryListLD.value?.size == 0){
//                scope.launch {
//                    try {
//                      val countries =  RestClient.apiService.getNationalities()
//                       // Timber.v("myApiResponse %s",countries.data)
////                        for (country in countries){
////                            countryDao.insert(country)
////                        }
//                        myApiResponse.postValue(MyApiResponse(400,"getCountriesRequest",myError.getFormattedError()))
//
//                    }catch (e: HttpException){
//                        val jerror:String = e.response()?.errorBody()?.string()!!
//                        Timber.v(jerror)
//                        myError = MyError(jerror)
//                        myApiResponse.postValue(MyApiResponse(400,"getCountriesRequest",myError.getFormattedError()))
//                    }catch (e : Exception){
//                        Timber.v(e.message)
//                        myApiResponse.postValue(MyApiResponse(400,"getCountriesRequest",myError.getFormattedError()))
//                    }
//               // }
//            }
//
//            return countryListLD
//        }

        
//        override suspend fun getConfigRegionList(): ResponseWrapper {
//            return RestClient.apiService.getConfigRegion(HashMap(), "".toRequestBody())
//        }



//        override suspend fun updatePassword(json: HashMap<String,String>): ResponseWrapper {
//            return RestClient.apiService.updatePassword(HashMap(),json.toString().toRequestBody())
//        }

//        override suspend fun userLogin(username: String,pass: String,grant: String): LoginWrapper {
//            var hashToken = HashMap<String,String>()//authtoken
//            //  hashToken.put("Authorization","Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI3Mjg4NDQwMzQiLCJleHAiOjE1Nzg0MjYzMDQsImlhdCI6MTU3ODQwODMwNH0.82MMK6r-gtxcq2TIEBBtoKC8RQJlErchWv3hOah4D6Me1D5ep2VuOnMa8db7GfNmefI-e9bQTV5DN8zvV0-fPA")
//            hashToken.put("Authorization", "Basic Y2hhbWFfY2xpZW50aWQ6WTJoaGJXRnpaV055WlhRPQ==")
//            return RestClient.apiService.userlogin(hashToken,username,pass,grant)
//        }

    }
