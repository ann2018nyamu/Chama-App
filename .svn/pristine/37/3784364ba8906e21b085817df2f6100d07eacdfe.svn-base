package com.ekenya.echama.ui.group.contribution


import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ekenya.echama.R
import com.ekenya.echama.databinding.ContributionWithdrawBinding
import com.ekenya.echama.databinding.FragmentCreateContributionBinding
import com.ekenya.echama.model.*
import com.ekenya.echama.util.*
import com.ekenya.echama.viewModel.GroupViewModel
import com.ekenya.echama.viewModel.MainViewModel
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


/**
 * A simple [Fragment] subclass.
 */
class CWithrawalFragment : Fragment() {
    private  var currentUser =  User()
    private var groupAccList: List<GroupAccount> = ArrayList()
    private lateinit var userViewModel: MainViewModel
    //lateinit var etContributionType:EditText
    lateinit var groupViewModel:GroupViewModel

    private var _binding: ContributionWithdrawBinding? = null
    private val binding get() = _binding!!
    private var accBalance = 0.0
    private var debitaccountid = 0
    private var contributionid = 0
    private var contributionname = ""



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = ContributionWithdrawBinding.inflate(inflater, container, false)
        val rootView = binding.root
        groupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java)
        userViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        contributionid = 1
        contributionname = "Corona Research Fund"

        arguments?.getString("contributionid")?.let {
            contributionid = it.toInt()
        }
        arguments?.getString("contributionname")?.let {
            contributionname = it
        }

        initData()
        initButton()
        initImageView()
        initEdittext()
        initTextView()


        return rootView
    }


    private fun initTextView() {
        binding.txtGroupAccount.setOnClickListener {
            selectGroupAcc()
        }
    }
    fun selectGroupAcc(){
        val accountAdapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, groupAccList) }
        accountAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        AlertDialog.Builder(this.requireContext())
            .setTitle("Select Group Account")
            .setAdapter(accountAdapter) { dialog: DialogInterface, which: Int ->
                binding.txtGroupAccount.text = groupAccList[which].toString()
                debitaccountid = groupAccList[which].accountid
                accBalance = groupAccList[which].accountbalance
                dialog.dismiss()
            }
            .create()
            .show()
    }


    private fun initEdittext() {

    }

    private fun initImageView() {


    }

    private fun initButton() {
       binding.btnWithdraw.setOnClickListener {
           if(validated()){
               var reason = ""
               if( binding.etxtReason.text.toString().isNotEmpty()){
                   reason = binding.etxtReason.text.toString()
               }
                   val withdrawData = HashMap<String, Any>()
                   withdrawData["amount"] =  binding.etxtAmount.text.toString().toDouble()
                   withdrawData["contributionid"] = contributionid
                   withdrawData["creditaccount"] = currentUser.phonenumber
                   withdrawData["debitaccountid"] =  debitaccountid
                   withdrawData["withdrawalreason"] = reason
                   CustomProgressBar.show(this.requireContext(),"Sending request")
                   groupViewModel.contributionWithdrawRequest(withdrawData)

           }
       }
    }



    private fun initData() {
        groupViewModel.getGroupAccounts()?.observe(viewLifecycleOwner, Observer { gAccList ->

//            groupAccList =  gAccList
//            groupAccStrList.clear()
//            for (cTypeItem in groupAccList){
//                Timber.v(cTypeItem.accountdetails?.getNameNAcc())
//                groupAccStrList.add(cTypeItem.accountdetails?.getNameNAcc()!!)
//            }

        })

        userViewModel.userLD.observe(viewLifecycleOwner, Observer { users ->
            if(users.isNotEmpty()){
                currentUser = users[0]
            }
        })


        groupViewModel.myGroupApiResponseLD.observe(viewLifecycleOwner, Observer { myApiResponse ->
            CustomProgressBar.hide()
            Timber.v("myApiResponseLD  ${myApiResponse.code} ${myApiResponse.requestName} ${myApiResponse.message}")
            if(myApiResponse.code == 200){

                if(myApiResponse.requestName.contentEquals("getGroupAccountsRequest")) {
                     groupAccList = myApiResponse.responseObj as ArrayList<GroupAccount>
                }

                if(myApiResponse.requestName.contentEquals("contributionWithdrawRequest")) {
                    successAlert("Success",myApiResponse.message)
                }

            }else  if(myApiResponse.code == 700) {
                CustomProgressBar.show(this.requireContext())
            }else{
                if(myApiResponse.message.isNotEmpty()){
                    ToastnDialog.toastError(this.context!!,"Error:"+myApiResponse.message)
                    if(myApiResponse.message.toLowerCase().contains("token expired")){
                        expiredTokenDialogue()
                    }
                }
            }
        })
    }

    private fun validated(): Boolean {
        if( debitaccountid == 0){
            ToastnDialog.toastError(this.requireContext(), "Select group account to withdraw from")
            return false
        }
        if( binding.etxtAmount.text.toString().isEmpty()){
            binding.tlAmount.error = "Enter Amount"
            return false
        }
        if(binding.etxtAmount.text.toString().toDouble() > accBalance ){
            binding.tlAmount.error = "Insufficient amount to withdraw. Current  balance ${DataUtil.gcurrency} ${accBalance}"
            return false
        }

        return true
    }


}
