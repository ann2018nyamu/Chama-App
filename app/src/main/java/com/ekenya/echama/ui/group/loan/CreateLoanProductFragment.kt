package com.ekenya.echama.ui.group.loan


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
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ekenya.echama.R
import com.ekenya.echama.databinding.FragmentAddLoanproductBinding
import com.ekenya.echama.databinding.FragmentCreateContributionBinding
import com.ekenya.echama.model.*
import com.ekenya.echama.util.*
import com.ekenya.echama.viewModel.GroupViewModel
import kotlinx.android.synthetic.main.fragment_add_loanproduct.*
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


/**
 * A simple [Fragment] subclass.
 */
class CreateLoanProductFragment : Fragment() {
    private  var contributionList: java.util.ArrayList<Contribution> = ArrayList()
    private var contribution: Contribution = Contribution()

    lateinit var groupViewModel:GroupViewModel
    private var _binding: FragmentAddLoanproductBinding? = null
    private val binding get() = _binding!!

    var penalty = Penalty()
    var reminder = Reminder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddLoanproductBinding.inflate(inflater, container, false)
        val rootView = binding.root

        groupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java)


        initData()
        initButton()
        initEdittext()

        return rootView
    }

    private fun initEdittext() {
        binding.etPaymentPeriodType.isFocusableInTouchMode = false
        binding.etPaymentPeriodType.setOnClickListener {
            binding.etPaymentPeriodType.isFocusableInTouchMode = true
            binding.etPaymentPeriodType.isFocusable = true
        }
        binding.etInterestType.isFocusable = false
        binding.etInterestType.setOnClickListener {
            binding.etInterestType.isFocusableInTouchMode = true
            binding.etInterestType.isFocusable = true
        }
        binding.etProductName.isFocusable = false
        binding.etProductName.setOnClickListener {
            binding.etProductName.isFocusableInTouchMode = true
            binding.etProductName.isFocusable = true
        }
        binding.etMinAmount.isFocusable = false
        binding.etMinAmount.setOnClickListener {
            binding.etMinAmount.isFocusableInTouchMode = true
            binding.etMinAmount.isFocusable = true
        }
        binding.etPaymentPeriod.isFocusable = false
        binding.etPaymentPeriod.setOnClickListener {
            binding.etPaymentPeriod.isFocusableInTouchMode = true
            binding.etPaymentPeriod.isFocusable = true
        }
        binding.etInterest.isFocusable = false
        binding.etInterest.setOnClickListener {
            binding.etInterest.isFocusableInTouchMode = true
            binding.etInterest.isFocusable = true
        }
        binding.etProductDesc.isFocusableInTouchMode = false
        binding.etProductDesc.isFocusable = false
        binding.etProductDesc.setOnClickListener {
            binding.etProductDesc.isFocusableInTouchMode = true
            binding.etProductDesc.isFocusable = true
        }
        binding.etMaxAmount.isFocusableInTouchMode = false
        binding.etMaxAmount.isFocusable = false
        binding.etMaxAmount.setOnClickListener {
            binding.etMaxAmount.isFocusableInTouchMode = true
            binding.etMaxAmount.isFocusable = true
        }


        binding.etPaymentPeriodType.setText("Month")
        binding.etPaymentPeriodType.setOnClickListener{selectPaymentPeriodType()}
        binding.etInterestType.setOnClickListener { selectInterestType() }
        binding.txtSelectContribution.setOnClickListener { selectContribution() }

//        binding.etProductName.setText("Wedding Loan")
//        binding.etMinAmount.setText("10000")
//        binding.etMaxAmount.setText("20000")
//        binding.etPaymentPeriod.setText("3")
//        binding.etInterest.setText("5")
//        binding.etProductDesc.setText("Wedding loan for members")

    }


    private fun initButton() {
        binding.btnSaveLoanProduct.setOnClickListener {
                saveLoanProduct()
        }

    }

    private fun saveLoanProduct() {
        if(validated()){
            val jsonContribution = HashMap<String,Any>()

            jsonContribution["contributionid"] = contribution.id
            jsonContribution["groupid"] = DataUtil.selectedGrpId
            jsonContribution["interesttype"] = etInterestType.text.toString().toLowerCase()
            jsonContribution["interestvalue"] = etInterest.text.toString()
            jsonContribution["max_principal"] = etMaxAmount.text.toString()
            jsonContribution["min_principal"] = etMinAmount.text.toString()
            jsonContribution["paymentperiod"] = etPaymentPeriod.text.toString()
            jsonContribution["paymentperiodtype"] = etPaymentPeriodType.text.toString().toLowerCase()
            jsonContribution["productname"] = etProductName.text.toString()
            jsonContribution["description"] = etProductDesc.text.toString()

            groupViewModel.createLoanProduct(jsonContribution)
        }
    }


    private fun initData() {
        val jsonContribution = HashMap<String,Any>()
        jsonContribution["groupid"] = DataUtil.selectedGrpId
        jsonContribution["page"] = 0
        jsonContribution["size"] = 100

        groupViewModel.getGrpContributions(jsonContribution).observe(viewLifecycleOwner, Observer {
            Timber.v("getGroupContribution size ${it.size}")
            contributionList.clear()
            if(it.isNotEmpty()){
                contributionList = it as ArrayList<Contribution>
            }
        })


        groupViewModel.myGroupApiResponseLD.observe(viewLifecycleOwner, Observer { myApiResponse ->
            CustomProgressBar.hide()
            Timber.v("myApiResponseLD  ${myApiResponse.code} ${myApiResponse.requestName} ${myApiResponse.message}")
            if(myApiResponse.code == 200){
                if(myApiResponse.requestName.contentEquals("createLoanProductRequest")){
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


    private fun validated(action:String = ""): Boolean {
         if(contribution.id == 0){
             ToastnDialog.toastError(this.requireContext(),"Please select contribution")
             return false
         }
        if(binding.etProductName.text.isNullOrEmpty()){
            binding.tlProductName.error = "Enter loan product name"
            return false
        }
        if(binding.etMinAmount.text.isNullOrEmpty()){
            binding.tlMinAmount.error = "Enter minimum amount"
            return false
        }
        if(binding.etMaxAmount.text.isNullOrEmpty()){
            binding.tlMaxAmount.error = "Enter maximum amount"
            return false
        }
        if(binding.etPaymentPeriod.text.isNullOrEmpty()){
             binding.tlPaymentPeriod.error = "Enter period type"
             return false
        }
        if(binding.etPaymentPeriodType.text.isNullOrEmpty()){
             binding.tlPaymentPeriodType.error = "Select payment period type"
             return false
         }
        if(binding.etInterestType.text.isNullOrEmpty()){
             binding.tlInterestType.error = "Select interest type"
             return false
         }
        if(binding.etInterest.text.isNullOrEmpty()){
             binding.tlInterest.error = "Enter loan product interest"
             return false
         }
        if(binding.etProductDesc.text.isNullOrEmpty()){
            binding.tlProductDesc.error = "Enter loan product description"
            return false
        }

        return true
    }


    fun selectContribution(){
        val accountAdapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, contributionList) }
        accountAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        AlertDialog.Builder(this.requireContext())
            .setTitle("Select Contribution")
            .setAdapter(accountAdapter) { dialog: DialogInterface, which: Int ->
                binding.txtSelectContribution.text = contributionList[which].name
                 contribution = contributionList[which]

                        dialog.dismiss()

            }
            .create()
            .show()
    }
    fun selectInterestType(){
        var interestTypeList = arrayListOf("Simple","Compound","Flat")

        val accountAdapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, interestTypeList) }
        accountAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        AlertDialog.Builder(this.requireContext())
            .setTitle("Select Interest Type")
            .setAdapter(accountAdapter) { dialog: DialogInterface, which: Int ->
                binding.etInterestType.setText(interestTypeList[which])
                        dialog.dismiss()
            }
            .create()
            .show()
    }
    fun selectPaymentPeriodType() {
        var paymentPeriodTypeList = arrayListOf("Month")
        val accountAdapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, paymentPeriodTypeList) }
        accountAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        AlertDialog.Builder(activity)
            .setTitle("Select Period")
            .setAdapter(accountAdapter) { dialog: DialogInterface, which: Int ->
                binding.etPaymentPeriodType.setText(paymentPeriodTypeList[which])
                dialog.dismiss()
            }
            .create()
            .show()

    }



}
