package com.ekenya.echama.network


import com.ekenya.echama.BuildConfig
import com.ekenya.echama.responseCalback.CountryResponse
import com.ekenya.echama.responseCalback.LoginWrapper
import com.ekenya.echama.responseCalback.ResponseWrapper
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface Webservice {
    @Headers("Content-Type: application/json")
    @POST("chama/mobile/loanproducts")
    suspend fun createLoanProduct(@HeaderMap headers: Map<String, String>,@Body body: RequestBody):ResponseWrapper

    @Headers("Content-Type: application/json")
    @POST("chama/mobile/memberpermissions")
    suspend fun updateMemberPermission(@HeaderMap headers: Map<String, String>,@Body body: RequestBody):ResponseWrapper
    //get all group loan product
    @GET("chama/mobile/memberpermissions")
    suspend fun getMemberPermission(@HeaderMap headers: Map<String, String>,
                               @Query("groupid") groupid: Int,
                               @Query("phonenumber") phonenumber: String): ResponseWrapper

    @Headers("Content-Type: application/json")
    @POST("chama/mobile/approveloan")
    suspend fun approveLoan(@HeaderMap headers: Map<String, String>,@Body body: RequestBody):ResponseWrapper

    @Headers("Content-Type: application/json")
    @POST("chama/mobile/applyloan")
    suspend fun applyLoan(@HeaderMap headers: Map<String, String>,@Body body: RequestBody):ResponseWrapper


    @Multipart
    @POST("chama/mobile/groups/payment/upload/{contributionid}")
    suspend fun uploadPaymentFiles(@HeaderMap headers: Map<String, String>,
                            @Path("contributionid")  groupId:Int,
                            @Part files: List<MultipartBody.Part?>?
    ): ResponseWrapper

    @Headers("Content-Type: application/json")
    @POST("chama/mobile/payloan")
    suspend fun recordLoanPayment(@HeaderMap headers: Map<String, String>,@Body body: RequestBody):ResponseWrapper
    @Headers("Content-Type: application/json")
    @POST("chama/mobile/makecontribution")
    suspend fun recordcontributionpayment(@HeaderMap headers: Map<String, String>,@Body body: RequestBody):ResponseWrapper

    //get all group loan product
    @GET("chama/mobile/loanpayments/{filter}")
    suspend fun getLoanPayment(@HeaderMap headers: Map<String, String>,
                                @Path("filter") filter: String,
                                @Query("filterid") groupid: String,
                                @Query("page") page: Int,
                                @Query("size") size: Int): ResponseWrapper
    @GET("chama/mobile/loanpaymentspendingapproval/{filter}")
    suspend fun getGrpLoanPaymentRequest(@HeaderMap headers: Map<String, String>,
                                @Path("filter") filter: String,
                                @Query("filterid") groupid: String,
                                @Query("page") page: Int,
                                @Query("size") size: Int): ResponseWrapper

    @GET("chama/mobile/pendingpayments/{filter}")
    suspend fun getGrpPaymentRequest(@HeaderMap headers: Map<String, String>,
                                @Path("filter") filter: String,
                                @Query("filterid") groupid: String): ResponseWrapper

    @GET("chama/mobile/approveloanpayment/{action}")
    suspend fun approveDeclineGrpLoanPayment(@HeaderMap headers: Map<String, String>,
                                         @Path("action") action:String,
                                         @Query("loanpaymentrecord") loanpaymentrecord: Int):ResponseWrapper
    @GET("chama/mobile/pendingpayments/{filter}")
    suspend fun approveDeclineGrpPayment(@HeaderMap headers: Map<String, String>,
                                         @Path("filter") filter:String,
                                         @Query("filterid") filterid: String):ResponseWrapper
    @GET("chama/mobile/pendingwithdrawals/{filter}")
    suspend fun approveDeclineGrpWithdrawal(@HeaderMap headers: Map<String, String>,
                                            @Path("filter") filter:String,
                                            @Query("filterid") filterid: String):ResponseWrapper
    @GET("chama/mobile/pendingwithdrawals/{filter}")
    suspend fun getGrpWithdrawalRequest(@HeaderMap headers: Map<String, String>,
                                @Path("filter") filter: String,
                                @Query("filterid") groupid: String,
                                @Query("page") page: Int,
                                @Query("size") size: Int): ResponseWrapper
    @GET("chama/mobile/loanspendingapproval/{filter}")
    suspend fun getGrpPendingLoanRequest(@HeaderMap headers: Map<String, String>,
                                @Path("filter") filter: String,
                                @Query("filterid") groupid: String,
                                @Query("page") page: Int,
                                @Query("size") size: Int): ResponseWrapper
    @GET("chama/mobile/dueloans")
    suspend fun getGrpBadLoans(@HeaderMap headers: Map<String, String>,
                                @Query("enddate") enddate: String ,
                                @Query("startdate") startdate: String ,
                                @Query("groupid") groupid: Int,
                                @Query("page") page: Int,
                                @Query("size") size: Int): ResponseWrapper
    @GET("chama/mobile/disbursedloans/{filter}")
    suspend fun getGrpLoans(@HeaderMap headers: Map<String, String>,
                                @Path("filter") filter: String = "group",
                                @Query("filterid") groupid: String,
                                @Query("page") page: Int,
                                @Query("size") size: Int): ResponseWrapper
    @GET("chama/mobile/loanproducts")
    suspend fun getLoanProducts(@HeaderMap headers: Map<String, String>,
                                @Query("groupid") groupid: Int,
                                @Query("page") page: Int,
                                @Query("size") size: Int): ResponseWrapper

    //Todo ask for api
    @Headers("Content-Type: application/json")
    @POST("chama/mobile/user/fullstatement")
    suspend fun getUserFullStatement(@HeaderMap headers: Map<String, String>, @Body body: RequestBody):ResponseWrapper

    //Todo ask for api
    @Headers("Content-Type: application/json")
    @POST("chama/mobile/groups/contribution/deactivate")
    suspend fun deactivateContribution(@HeaderMap headers: Map<String, String>, @Body body: RequestBody):ResponseWrapper


    //Todo ask for api
    @GET("chama/mobile/groups/penalties")
    suspend fun getCPenalties(@HeaderMap headers: Map<String, String>,
                                @Query("contributionid") groupid: Int,
                                @Query("page") page: Int,
                                @Query("size") size: Int): ResponseWrapper



    @Headers("Content-Type: application/json")
    @POST("chama/mobile/makewithdrawal")
    suspend fun contributionWithdraw(@HeaderMap headers: Map<String, String>,@Body body: RequestBody):ResponseWrapper

    @Headers("Content-Type: application/json")
    @POST("chama/mobile/userwithdrawal")
    suspend fun userWithdraw(@HeaderMap headers: Map<String, String>,@Body body: RequestBody):ResponseWrapper

    @Headers("Content-Type: application/json")
    @POST("chama/mobile/makepayment")
    suspend fun makePayment(@HeaderMap headers: Map<String, String>,@Body body: RequestBody):ResponseWrapper

    @Headers("Content-Type: application/json")
    @POST("chama/mobile/groups/invites")
    suspend fun addGroupMembers(@HeaderMap headers: Map<String, String>,@Body body: RequestBody):ResponseWrapper


    @Headers("Content-Type: application/json")
    @POST("chama/mobile/groups/leave")
    suspend fun exitGroup(@HeaderMap headers: Map<String, String>, @Body body: RequestBody):ResponseWrapper

    //get all member's group
    @GET("chama/mobile/groups/members")
    suspend fun getGroupMembers(@HeaderMap headers: Map<String, String>,
                                @Query("groupid") groupid: Int,
                                @Query("page") page: Int,
                                @Query("size") size: Int): ResponseWrapper

    @Headers("Content-Type: application/json")
    @POST("chama/mobile/contributions")
    suspend fun createGrpContribution(@HeaderMap headers: Map<String, String>, @Body body: RequestBody):ResponseWrapper


    @Headers("Content-Type: application/json")
    @POST("chama/mobile/groupaccount")
    suspend fun addGroupAccount(@HeaderMap headers: Map<String, String>, @Body body: RequestBody):ResponseWrapper

    @Headers("Content-Type: application/json")
    @GET("chama/mobile/groupaccount")
    suspend fun getGroupAccounts(@HeaderMap headers: Map<String, String>,
                                 @Query("groupid") groupid: String):ResponseWrapper

    @GET("chama/mobile/accounttypes")
    suspend fun getAccountTypes(@HeaderMap headers: Map<String, String>): ResponseWrapper

    @GET("chama/mobile/deleteAccount")
    suspend fun deleteUser(@HeaderMap headers: Map<String, String>,@Query("phonenumber") phonenumber: String): ResponseWrapper

    @GET("chama/mobile/amounttypes")
    suspend fun getAmountType(@HeaderMap headers: Map<String, String>): ResponseWrapper

    //get all member's group
    @GET("chama/mobile/group/members")
    suspend fun getAllMembers(@HeaderMap headers: Map<String, String>,
                              @Query("groupid") groupid: Int,
                              @Query("page") page: Int,
                              @Query("size") size: Int):ResponseWrapper

    //get contribution schedule
    @GET("chama/mobile/scheduletypes")
    suspend fun getContributionSchedule(@HeaderMap headers: Map<String, String>):ResponseWrapper

    //get contribution types
    @GET("chama/mobile/contributiontypes")
    suspend fun getContributionType(@HeaderMap headers: Map<String, String>):ResponseWrapper

    //get group transactions - ministatement
    @GET("chama/mobile/transactions/contribution")
    suspend fun getContributionTrx(@HeaderMap headers: Map<String, String>,
                                     @Query("filterid") filterid: Int,
                                     @Query("page") page: Int,
                                     @Query("size") size: Int):ResponseWrapper

    //Get group contributions
    @GET("chama/mobile/contributions")
    suspend fun getGrpContributions(@HeaderMap headers: Map<String, String>,
                                   @Query("groupid") groupid: Int,
                                   @Query("page") page: Int,
                                   @Query("size") size: Int):ResponseWrapper

    //get group details
    @GET("chama/mobile/groups")
    suspend fun getGroupDetails(@HeaderMap headers: Map<String, String>,
                                     @Query("groupid") groupid: Int,
                                     @Query("page") page: Int,
                                     @Query("size") size: Int):ResponseWrapper
    //get group transactions - ministatement
    @GET("chama/mobile/transactions/group")
    suspend fun getGroupTransactions(@HeaderMap headers: Map<String, String>,
                                    @Query("filterid") filterid: Int,
                                    @Query("page") page: Int,
                                    @Query("size") size: Int):ResponseWrapper

    //Accept/Decline group invites

    @GET("chama/mobile/groups/invites/{action}")
    suspend fun acceptDeclineGrpInvite(@HeaderMap headers: Map<String, String>,
                                @Path("action") action:String,
                                @Query("inviteid") inviteid: Int):ResponseWrapper


    //get user transactions - ministatement
    @GET("chama/mobile/transactions/user")
    suspend fun getUserTransactions(@HeaderMap headers: Map<String, String>,
                                    @Query("page") page: Int,
                                    @Query("size") size: Int):ResponseWrapper
    @Headers("Content-Type: application/json")
    @PUT("chama/mobile/user")
    suspend fun updateUserDetails(@HeaderMap headers: Map<String, String>, @Body body: RequestBody):ResponseWrapper

    @GET("chama/mobile/user")
    suspend fun getUserDetails(@HeaderMap headers: Map<String, String>): ResponseWrapper
    @GET("chama/mobile/req/forgotpassword")
    suspend fun resetPass(
        @Query("account") account: String,
        @Query("identification") identification: String):ResponseWrapper

    @Multipart
    @POST("chama/mobile/groups/upload/{groupid}")
    suspend fun uploadFiles(@HeaderMap headers: Map<String, String>,
        @Path("groupid")  groupId:Int,
        @Part files: List<MultipartBody.Part?>?
    ): ResponseWrapper

    @Multipart
    @POST("chama/mobile/groups/upload/{groupid}")
    suspend fun uploadFile(@HeaderMap headers: Map<String, String>,
        @Path("groupid")  groupId:Int,
        @Part file: MultipartBody.Part?
       // @Part("file") name: RequestBody?
    ): ResponseWrapper
    @Headers("Content-Type: application/json")
    @POST("chama/mobile/groups")
    suspend fun createGroup(@HeaderMap headers: Map<String, String>, @Body body: RequestBody):ResponseWrapper

    //get all member's group
    @GET("chama/mobile/groups")
    suspend  fun getAllGroups(@HeaderMap headers: Map<String, String>,@Query("page") page: Int,
                              @Query("size") size: Int):ResponseWrapper
    //get mygroup invites
    @GET("chama/mobile/groups/list/invites")
    suspend fun getGrpInvites(@HeaderMap headers: Map<String, String>,@Query("page") page: Int,
                              @Query("size") size: Int):ResponseWrapper
    //get group's invites
    @GET("chama/mobile/groups/list/invites")
    suspend fun getGrpInvites(@HeaderMap headers: Map<String, String>,
                              @Query("groupid") groupid: String,
                              @Query("page") page: Int,
                              @Query("size") size: Int):ResponseWrapper

//    @GET("chama/mobile/groups/invites")
//    suspend fun getGrpInvites(@HeaderMap headers: Map<String, String>, @Query("page") page: Int,
//                              @Query("size") size: Int):ResponseWrapper
    // Password changed
    @Headers("Content-Type: application/json")
    @POST("chama/mobile/req/updatepassword")
    suspend fun updatePassword(@Body body: RequestBody):ResponseWrapper

    @Headers("Content-Type: application/json")
    @POST("chama/mobile/req/verifyotp")
    suspend  fun verificationOtp(@Body  json:RequestBody):ResponseWrapper

    @Headers("Content-Type: application/json")
    @POST("chama/mobile/req/newuser")
    suspend fun registerMember(@Body  json:RequestBody):ResponseWrapper

    @GET("chama/mobile/req/countries")
    suspend fun getNationalities(): CountryResponse

    @Headers("Authorization: Basic ${BuildConfig.AUTH_URL_TOKEN}")
    @FormUrlEncoded
    @POST("auth/oauth/token")
    suspend  fun getAuthUser(@FieldMap  data:HashMap<String,String>?): LoginWrapper

//OLD REQUESTS
//    @POST("mobile/config/get-nationalites")
//    fun gettodoLst(@HeaderMap headers: Map<String, String>, @Body body: RequestBody):Deferred<List<String>>
   // mobile/config/get-nationalites
    @POST("chama/mobile/config/get-nationalites")
    fun gettodoLst(@HeaderMap headers: Map<String, String>, @Body body: RequestBody):List<String>
    //mobile/config/get-nationalites

    @POST("chama/mobile/config/get-regions")
    fun getConfigRegion(@HeaderMap headers: Map<String, String>, @Body body: RequestBody):ResponseWrapper





    @FormUrlEncoded
    @POST("auth/oauth/token")
    suspend fun userlogin(
        @HeaderMap headers: Map<String, String>,
        @Field("username") username:String,
        @Field("password") password:String,
        @Field("grant_type") grant_type:String):LoginWrapper
    //fun userlogin(@HeaderMap headers: Map<String, String>, @Body body: RequestBody):ResponseWrapper

    @POST("chama/mobile/config/get-countries")
    suspend fun getCountries(@HeaderMap headers: Map<String, String>, @Body body: RequestBody):ResponseWrapper

    @GET("chama/mobile/req/countries")
    fun getCountryInfo()

    @POST("chama/mobile/config/get-group-types")
   suspend fun getGroupType(@HeaderMap headers: Map<String, String>, @Body body: RequestBody):ResponseWrapper


    // TODO INTEGRATE WITH PAYMENT GATEWAY create group contributions




    //Send Out Group Invites
    @POST("chama/mobile/groups/invites")
    suspend fun sendGrpInvites(@HeaderMap headers: Map<String, String>)



}