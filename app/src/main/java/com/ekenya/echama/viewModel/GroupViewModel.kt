package com.ekenya.echama.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekenya.echama.model.*
import com.ekenya.echama.repository.GroupRepository
import okhttp3.MultipartBody

/**
 * Group viewmodel
 * @author Oscar Muigai
 */
class GroupViewModel :ViewModel(){


    var  groupLD: LiveData<List<Group>>
    var  groupInvitesLD: LiveData<List<GroupInvite>>
    val groupRepository : GroupRepository =  GroupRepository(viewModelScope )
    val myGroupApiResponseLD =  groupRepository.myApiResponse
    var invitePage = 0
    var inviteSize = 10
    private val gPage: Int = 0
    private val gSize: Int = 10

    init {
        groupLD = groupRepository.groupLD
        groupInvitesLD = groupRepository.groupInvitesLD
    }

    /**
     * method to get member group invites request
     * @param json consist member Id and group Id
     * @return mediator livedata consisting list of invites
     */
     fun getGrpInvites(){
       var gData =  HashMap<String,Any>()
        gData["page"] = invitePage
        gData["size"] = inviteSize
         groupRepository.getGrpInvites(gData)

//        when(results){
//            is ResultWrapper.NetworkError -> grpInvitesMediatorLiveData.value=ViewModelWrapper.error("Network error occurred")
//            is ResultWrapper.GenericError -> grpInvitesMediatorLiveData.value=ViewModelWrapper.error("Error occurred")
//            is ResultWrapper.Success -> returnData(grpInvitesMediatorLiveData,results.value)
//        }
//        return grpInvitesMediatorLiveData
    }

    /**
     * function to create new group
     * @param jjson group details
     */
     fun createNewGroup(json: HashMap<String,Any>){

         groupRepository.createGroup(json)
       // return results
//        when(results){
//            is ResultWrapper.NetworkError -> createGroupLiveData.value=ViewModelWrapper.error("Error")
//            is ResultWrapper.GenericError -> createGroupLiveData.value=ViewModelWrapper.error("GError")
//            is ResultWrapper.Success -> returnData(createGroupLiveData,results.value)//registerMemberMediatorData.value=ViewModelWrapper.response(results.value.listData2().toString())
//        }

        //return createGroupLiveData
    }

    /**
     * suspend function to return all member group
     * @param json group request
     */
     fun getAllGroups(page:Int, size:Int){
         groupRepository.getAllGroups(page,size)
        //return results
//        when(results){
//            is ResultWrapper.NetworkError -> allGroupsMediatorLiveData.value = ViewModelWrapper.error("Error occurred")
//            is ResultWrapper.GenericError -> allGroupsMediatorLiveData.value = ViewModelWrapper.error("Error occurred")
//            is ResultWrapper.Success -> returnData(allGroupsMediatorLiveData,results.value)
//        }

       // return  allGroupsMediatorLiveData
    }

    /**
     * get all members in a group function
     * @param json group id details
     * @return allgroupmediatorlive data
     */

     fun getAllMembers(json: HashMap<String,Any>){
         groupRepository.getAllMembers(json)

//        when(results){
//            is ResultWrapper.NetworkError -> allMembersMediatorData.value=ViewModelWrapper.error("Error occurred")
//            is ResultWrapper.GenericError -> allMembersMediatorData.value = ViewModelWrapper.error("Error occurred")
//            is ResultWrapper.Success -> returnData(allMembersMediatorData,results.value)
//        }
//        return allMembersMediatorData
    }
    /**
     * get all group transactions
     * @param json group id details
     * @return allgroupmediatorlive data
     */

    fun getContributionTransactions(json: HashMap<String,Any>):LiveData<List<Transaction>>{
        return  groupRepository.getContributionTransactions(json)
    }
    /**
     * get all group transactions
     * @param json group id details
     * @return allgroupmediatorlive data
     */

    fun getGroupTransactions(json: HashMap<String,Any>):LiveData<List<Transaction>>{
       return  groupRepository.getGroupTransactions(json)
    }

    /**
     * get all contributions per group
     * @param json group data, request size and request page
     * @return contributionmediatorlive data
     */

     fun getGrpContributions(json: HashMap<String,Any>):LiveData<List<Contribution>>{
        return groupRepository.getGrpContributions(json)

//        when(results){
//            is ResultWrapper.NetworkError -> allGroupContributionMediatorData.value = ViewModelWrapper.error("Error occurred")
//            is ResultWrapper.GenericError -> allGroupContributionMediatorData.value = ViewModelWrapper.error("Generic Error occurred")
//            is ResultWrapper.Success -> returnData(allGroupContributionMediatorData,results.value)
//        }
//        return allGroupContributionMediatorData
    }

    /**
     * get contribution type
     * @return contributiion type mediator livedata
     */

     fun getContributionType():LiveData<List<ContributionType>>{
         return groupRepository.getContributionTypes()

//        when(results){
//            is ResultWrapper.NetworkError -> contributionTypeMediatorData.value = ViewModelWrapper.error("Error occurred")
//            is ResultWrapper.GenericError -> contributionTypeMediatorData.value = ViewModelWrapper.error("Error Occurred")
//            is ResultWrapper.Success -> returnData(contributionTypeMediatorData,results.value)
//        }
//        return contributionTypeMediatorData

    }

    /**
     * function to get contribution schedule
     * @return contribution schedule mediator livedata
     */

     fun getContributionSchedules():LiveData<List<ScheduleType>>{
        return groupRepository.getContributionSchedules()

//        when(results){
//            is ResultWrapper.NetworkError -> contributionScheduleMediatorLiveData.value = ViewModelWrapper.error("Network Error occurred")
//            is ResultWrapper.GenericError -> contributionScheduleMediatorLiveData.value = ViewModelWrapper.error("Generic Error Occurred")
//            is ResultWrapper.Success -> returnData(contributionScheduleMediatorLiveData,results.value)
//        }
//        return contributionScheduleMediatorLiveData
    }

    /**
     * function to create group contribution
     * @return contribution status mediator livedata
     */
     fun createContribution(json: HashMap<String,Any>){
         groupRepository.createContribution(json)
    }
    /**
     * function to create group contribution
     * @return contribution status mediator livedata
     */
     fun createLoanProduct(json: HashMap<String,Any>){
         groupRepository.createLoanProduct(json)
    }



    /**
     * method to get member group invites request
     * @param json consist member Id and group Id
     * @return mediator livedata consisting list of invites
     */
     fun acceptDeclineGrpInvite(json: HashMap<String,Any>){
         groupRepository.acceptDeclineGrpInvite(json)
    }
    fun approveDeclineGrpPaymentRequest(json: HashMap<String,Any>){
         groupRepository.approveDeclineGrpPaymentRequest(json)
    }
    fun approveDeclineGrpLoanPaymentRequest(json: HashMap<String,Any>){
         groupRepository.approveDeclineGrpLoanPaymentRequest(json)
    }

    fun approveDeclineGrpWithdrawal(json: HashMap<String,Any>){
         groupRepository.approveDeclineGrpWithdrawal(json)
    }

    fun uploadGrpFiles(groupid:Int,fileToUpload: List<MultipartBody.Part>) {
        groupRepository.uploadGrpFiles(groupid,fileToUpload)
    }
    fun uploadPaymentReceipt(contributionId:Int,fileToUpload: List<MultipartBody.Part>) {
        groupRepository.uploadPaymentReceipt(contributionId,fileToUpload)
    }

    fun getGroupDetails(groupid: Int): LiveData<Group>? {
        val jsonGroupDetails = HashMap<String,Any>()
        jsonGroupDetails["groupid"] =groupid
        jsonGroupDetails["page"] = gPage
        jsonGroupDetails["size"] = gSize
      return  groupRepository.getGroupDetails(jsonGroupDetails)
    }

    fun getAmountType() {
        groupRepository.getAmountType()
    }

    fun getAccountTypes() :LiveData<List<AccountType>>?{
       return groupRepository.getAccountTypes()

    }

    fun addGroupAccount(gAcc: GroupAccount) {
        groupRepository.addGroupAccount(gAcc)
    }

    fun getGroupMembers(json: HashMap<String, Any>):LiveData<List<Member>>? {
        return groupRepository.getGroupMembers(json)
    }


    fun addGroupMembers(jsonGroupDetails: HashMap<String, Any>) {
        groupRepository.addGroupMembers(jsonGroupDetails)
    }

    fun exitGroup(jsonDetails: HashMap<String, Any>) {
        groupRepository.exitGroup(jsonDetails)
    }

    fun contributionWithdrawRequest(withdrawData: HashMap<String, Any>) {
        groupRepository.contributionWithdrawRequest(withdrawData)
    }

    fun getGroupAccounts():LiveData<List<GroupAccount>>? {
        return groupRepository.getGroupAccounts()
    }

    fun getCPenalties(pData: HashMap<String, Any>) {
        groupRepository.getCPenalties(pData)
    }

    fun deactivateContribution(cData: HashMap<String, Any>) {
        groupRepository.deactivateContribution(cData)
    }

    fun getLoanProducts(jsonDetails: HashMap<String, Any>): LiveData<List<LoanProduct>>? {
       return  groupRepository.getLoanProducts(jsonDetails)
    }

    fun getGrpLoans(jsonDetails: HashMap<String, Any>):  LiveData<List<LoanApproved>>? {
        return  groupRepository.getGrpLoans(jsonDetails)
    }
    fun getGrpBadLoans(jsonDetails: HashMap<String, Any>)  {
          groupRepository.getGrpBadLoans(jsonDetails)
    }
    fun getLoanPayment(jsonDetails: HashMap<String, Any>) {
          groupRepository.getLoanPayment(jsonDetails)
    }
    fun getGrpPendingLoanRequest(jsonDetails: HashMap<String, Any>) {
          groupRepository.getGrpPendingLoanRequest(jsonDetails)
    }
    fun getGrpWithdrawalRequest(jsonDetails: HashMap<String, Any>) {
          groupRepository.getGrpWithdrawalRequest(jsonDetails)
    }
    fun getGrpPaymentRequest(jsonDetails: HashMap<String, Any>) {
          groupRepository.getGrpPaymentRequest(jsonDetails)
    }
    fun getGrpLoanPaymentRequest(jsonDetails: HashMap<String, Any>) {
          groupRepository.getGrpLoanPaymentRequest(jsonDetails)
    }

    fun recordPayment(paymentData: HashMap<String, Any>) {
        groupRepository.recordPayment(paymentData)
    }
    fun recordLoanPayment(paymentData: HashMap<String, Any>) {
        groupRepository.recordLoanPayment(paymentData)
    }

    fun applyLoan(jsonLoanApply: HashMap<String, Any>) {
        groupRepository.applyLoan(jsonLoanApply)
    }

    fun approveLoan(loanData: java.util.HashMap<String, Any>) {
        groupRepository.approveLoan(loanData)
    }

    fun getMemberPermission(memberDetails: java.util.HashMap<String, Any>) {
        groupRepository.getMemberPermission(memberDetails)
    }

    fun updateMemberPermission(jsonGroupDetails: java.util.HashMap<String, Any>) {
        groupRepository.updateMemberPermission(jsonGroupDetails)
    }


}
