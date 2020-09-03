package com.ekenya.echama.ui.group.loan


import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.ekenya.echama.R
import com.ekenya.echama.adapter.GroupContributionAdapter
import com.ekenya.echama.adapter.LoansAdapter
import com.ekenya.echama.databinding.FragmentGroupContributionBinding
import com.ekenya.echama.databinding.FragmentGroupWalletBinding
import com.ekenya.echama.databinding.FragmentLoansBinding
import com.ekenya.echama.databinding.LoanApplyBinding
import com.ekenya.echama.inc.AppInfo
import com.ekenya.echama.model.*
import com.ekenya.echama.util.*
import com.ekenya.echama.viewModel.GroupViewModel
import com.ekenya.echama.viewModel.ViewModelWrapper
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.w3c.dom.Text
import timber.log.Timber
import java.security.acl.Group
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class LoanApplyFragment : Fragment() {
    private lateinit var contributionList: ArrayList<Loan>
   // lateinit var rcvContribution : RecyclerView
    lateinit var groupViewModel : GroupViewModel
    lateinit var loanAdapter:LoansAdapter
    private var _binding: LoanApplyBinding? = null
    private val binding get() = _binding!!
    var remindermillis:Long = 0L
    var reminder = Reminder()
    val reminderDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.ENGLISH)
    var scheduleList:ArrayList<String> = ArrayList()
    var loanProduct = LoanProduct()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = LoanApplyBinding.inflate(inflater, container, false)
        val rootView = binding.root
        //val rootView= inflater.inflate(R.layout.fragment_group_contribution, container, false)
        groupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java)
      //  val btnCreateContribution = rootView.findViewById(R.id.btnCreate) as Button
      //  btnCreateContribution.setOnClickListener { findNavController().navigate(R.id.nav_create_contribution) }
       // var loanStr =  "{\"productid\":1,\"productname\":\"Emergency Loan\",\"description\":\"Quick loan with immediate disbursal\",\"max_principal\":40000.0,\"min_principal\":1000.0,\"interesttype\":\"simple interest\",\"interestvalue\":10.0,\"paymentperiod\":2,\"paymentperiodtype\":\"months\",\"contributionid\":3,\"contributionname\":\"Machakos Flats\",\"groupname\":\"AllinOne Investments\",\"groupid\":1}"
       // loanProduct = loanProduct.fromJson(loanStr)
        arguments?.getString("loanproduct").let {
            loanProduct = loanProduct.fromJson(it.toString())
        }

        initData()
        initButton()
        initReminder()
        return rootView
    }
    private fun initReminder() {


        binding.tvLoanProductName.text = loanProduct.loanProductName
        binding.tvLoanProductDesc.text = loanProduct.loanProductDesc
        binding.tvMinMaxAmount.text = "Loan limit:\n"+loanProduct.getFMinumAmount()+" - "+loanProduct.getFMaximumAmount()
        binding.tvInterestRate.text = "Interest type:\n"+loanProduct.interestType+": "+loanProduct.getInterestRateValue()
        binding.tvInterestPeriod.text = "Duration:\n"+loanProduct.period.toInt()+" "+loanProduct.paymentperiodtype.capitalize()

        binding.txtReminderStartDate.setOnClickListener{ getReminderStartDate() }
        binding.txtReminderEndDate.setOnClickListener{getReminderEndDate()}
        binding.txtPickReminderSchedule.setOnClickListener{selectReminderSchedule()}

    }
    /**
     * get calendar date in DD/MM/YYYY format
     */
    private fun getReminderEndDate() {
        if (remindermillis != 0L) {
            val c = Calendar.getInstance()
            val calendar = Calendar.getInstance()
            val mYear = calendar.get(Calendar.YEAR)
            val mMonth = calendar.get(Calendar.MONTH)
            val mDay = calendar.get(Calendar.DAY_OF_MONTH)

            val dpDialog = DatePickerDialog(activity!!, R.style.datepicker,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    val newDate = Calendar.getInstance()
                    newDate[year, month] = dayOfMonth
                    val endCron = CroneJob()
                    endCron.month = month.toString()
                    endCron.dayofmonth = dayOfMonth.toString()
                    // now show the time picker
                    TimePickerDialog(
                        this.requireContext(), R.style.datepicker,
                        TimePickerDialog.OnTimeSetListener { view, h, min ->
                            newDate[Calendar.HOUR_OF_DAY] = h
                            newDate[Calendar.MINUTE] = min
                            endCron.hour = h.toString()
                            endCron.minute = min.toString()
                            reminder.enddate = endCron
                            binding.txtReminderEndDate.text =
                                reminderDateFormat.format(newDate.time)
                        }, newDate[Calendar.HOUR_OF_DAY],
                        newDate[Calendar.MINUTE], false
                    ).show()

                }, mYear, mMonth, mDay)

            dpDialog.datePicker.minDate = remindermillis
            dpDialog.show()
        } else {
            Toast.makeText(activity, "Please select reminder start date  first", Toast.LENGTH_SHORT).show()
            //Utils.showDialogWarning(getActivity(), "TBLAPP", "Please select pay bill date first");
        }

    }
    private fun getReminderStartDate() {
        val calendar = Calendar.getInstance()
        val mYear = calendar.get(Calendar.YEAR)
        val mMonth = calendar.get(Calendar.MONTH)
        val mDay = calendar.get(Calendar.DAY_OF_MONTH)
        val dpDialog = DatePickerDialog(activity!!, R.style.datepicker,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val newDate = Calendar.getInstance()
                newDate[year, month] = dayOfMonth
                val startCron = CroneJob()
                startCron.month = month.toString()
                startCron.dayofmonth = dayOfMonth.toString()
                // now show the time picker
                TimePickerDialog(
                    this.requireContext(), R.style.datepicker,
                    TimePickerDialog.OnTimeSetListener { view, h, min ->
                        newDate[Calendar.HOUR_OF_DAY] = h
                        newDate[Calendar.MINUTE] = min
                        startCron.hour = h.toString()
                        startCron.minute = min.toString()
                        reminder.startdate = startCron
                        remindermillis = newDate.timeInMillis
                        binding.txtReminderStartDate.text = reminderDateFormat.format(newDate.time)
                    }, newDate[Calendar.HOUR_OF_DAY],
                    newDate[Calendar.MINUTE], false
                ).show()

            }
            , mYear, mMonth, mDay)
        dpDialog.datePicker.minDate = calendar.timeInMillis
        dpDialog.show()
    }
    fun selectReminderSchedule(){

       var reminderScheduleList = arrayOf("Daily","Weekly","Monthly")

        val accountAdapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, reminderScheduleList) }
        accountAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        AlertDialog.Builder(this.requireContext())
            .setTitle("Select Reminder Schedule")
            .setAdapter(accountAdapter) { dialog: DialogInterface, which: Int ->
                binding.txtPickReminderSchedule.text = reminderScheduleList[which]

                var cronJob = CroneJob()
                reminder.startdate?.hour?.let {
                     cronJob.hour = it
                }

                 reminder.startdate?.minute?.let {
                     cronJob.minute = it
                }
                if (reminderScheduleList[which].contains("Daily")) {
                } else if (reminderScheduleList[which].contains("Weekly")) {
                    cronJob.dayofweek = "0"
                } else if (reminderScheduleList[which].contains("Monthly")) {
                     reminder.startdate?.dayofmonth?.let {
                        cronJob.dayofmonth = it
                    }
                    if(reminder.startdate?.dayofmonth.isNullOrBlank()){
                        ToastnDialog.toastError(this.requireContext(),"Pick a starting date first")
                        binding.txtPickReminderSchedule.text = getString(R.string.pick_a_schedule)
                        cronJob = CroneJob()
                    }
                }

                reminder.frequency = cronJob
                dialog.dismiss()

            }
            .create()
            .show()
    }

    private fun initButton() {
        binding.btnApplyLoan.setOnClickListener {
             if(validateInfo()){
                 val jsonLoanApply = HashMap<String,Any>()
                 jsonLoanApply["amount"] = binding.etxtAmount.text.toString()
                 jsonLoanApply["loanproduct"] = loanProduct.loanProductId
                 jsonLoanApply["reminder"] = reminder
                 CustomProgressBar.show(this.requireContext(),"Sending request")
                 groupViewModel.applyLoan(jsonLoanApply)

             }
        }
    }

    private fun validateInfo(): Boolean {
        if(binding.etxtAmount.text.toString().trim().isEmpty()){
            binding.tlAmount.error = "Please enter amount"
            return false
        }
        if(binding.etxtAmount.text.toString().toDouble() < loanProduct.minimumAmount){
            binding.tlAmount.error = "Please enter correct amount. Minimum loan amount is ${loanProduct.getFMinumAmount()}"
            return false
        }
        if(binding.etxtAmount.text.toString().toDouble() > loanProduct.maximumAmount){
            binding.tlAmount.error = "Please enter correct amount. Maximum loan amount is ${loanProduct.getFMaximumAmount()}"
            return false
        }

        if(binding.txtReminderStartDate.text.toString().contentEquals(resources.getString(R.string.start_date))){
            ToastnDialog.toastError(this.requireContext(),"Please select reminder due date")
            return false
        }

        if(binding.txtReminderEndDate.text.toString().contentEquals(resources.getString(R.string.start_date))){
            ToastnDialog.toastError(this.requireContext(),"Please select reminder due date")
            return false
        }

        if(binding.txtPickReminderSchedule.text.toString().contentEquals(resources.getString(R.string.pick_a_schedule))){
            ToastnDialog.toastError(this.requireContext(),"Please select reminder schedule")
            return false
        }
        reminder.applied = true

        return true
    }

    private fun initGroupContributionRV() {

    }

    private fun initData() {
        groupViewModel.myGroupApiResponseLD.observe(viewLifecycleOwner, Observer { myApiResponse ->
            CustomProgressBar.hide()
            Timber.v("myApiResponseLD  ${myApiResponse.code} ${myApiResponse.requestName} ${myApiResponse.message}")
            if(myApiResponse.code == 200){
                if(myApiResponse.requestName.contentEquals("applyLoanRequest")) {
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

}
