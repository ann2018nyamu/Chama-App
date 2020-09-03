package com.ekenya.echama.ui.group.loan


import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.ekenya.echama.R
import com.ekenya.echama.adapter.BadLoanAdapter
import com.ekenya.echama.adapter.GroupContributionAdapter
import com.ekenya.echama.adapter.LoanApprovedAdapter
import com.ekenya.echama.databinding.FragmentBadloansBinding
import com.ekenya.echama.databinding.FragmentGroupContributionBinding
import com.ekenya.echama.databinding.FragmentGroupWalletBinding
import com.ekenya.echama.databinding.FragmentLoansBinding
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
import kotlin.collections.HashMap

/**
 * A simple [Fragment] subclass.
 */
class BadLoansFragment : Fragment() {
    var startmillis:Long = 0L
    val fStatementDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    private lateinit var badLoanList: ArrayList<LoanApproved>
   // lateinit var rcvContribution : RecyclerView
    lateinit var groupViewModel : GroupViewModel
    lateinit var loanApprovedAdapter: BadLoanAdapter
    private var _binding: FragmentBadloansBinding? = null
    var startDate = Calendar.getInstance()
    var endDate = Calendar.getInstance()

    private val binding get() = _binding!!
    val cPage = 0
    val cSize = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBadloansBinding.inflate(inflater, container, false)
        val rootView = binding.root
        //val rootView= inflater.inflate(R.layout.fragment_group_contribution, container, false)
        groupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java)
      //  val btnCreateContribution = rootView.findViewById(R.id.btnCreate) as Button
      //  btnCreateContribution.setOnClickListener { findNavController().navigate(R.id.nav_create_contribution) }
        initView()
        initData()
        initButton()

        return rootView
    }

    private fun initView() {
        startmillis =  startDate.timeInMillis

        endDate.add(Calendar.DATE, -14);
        binding.txtFstatementStartDate.text = fStatementDateFormat.format(endDate.time)
        binding.txtFstatementEndDate.text = fStatementDateFormat.format(startDate.time)

        binding.txtFstatementStartDate.setOnClickListener{ getFStartDate() }
        binding.txtFstatementEndDate.setOnClickListener{getFEndDate()}
    }
    private fun  getFEndDate() {
        if (startmillis != 0L) {
            val dpDialog = DatePickerDialog(activity!!, R.style.datepicker,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    val newDate = Calendar.getInstance()
                    newDate[year, month] = dayOfMonth
                    startDate = newDate

                    binding.txtFstatementEndDate.text = fStatementDateFormat.format(newDate.time)

                }, startDate.get(Calendar.YEAR),  startDate.get(Calendar.MONTH),  startDate.get(Calendar.DAY_OF_MONTH))
            dpDialog.datePicker.minDate = endDate.timeInMillis
            dpDialog.datePicker.maxDate = Calendar.getInstance().timeInMillis

            dpDialog.show()
        } else {
            Toast.makeText(activity, "Please select reminder start date  first", Toast.LENGTH_SHORT).show()
        }

    }
    private fun getFStartDate() {
        val dpDialog = DatePickerDialog(activity!!, R.style.datepicker,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val newDate = Calendar.getInstance()
                newDate[year, month] = dayOfMonth
                startmillis = newDate.timeInMillis
                endDate = newDate
                binding.txtFstatementStartDate.text = fStatementDateFormat.format(newDate.time)
            },  endDate.get(Calendar.YEAR),  endDate.get(Calendar.MONTH),  endDate.get(Calendar.DAY_OF_MONTH))

        dpDialog.datePicker.maxDate = Calendar.getInstance().timeInMillis
        dpDialog.show()
    }

    private fun initButton() {
      binding.btnSearch.setOnClickListener {
          if(endDate.timeInMillis > startDate.timeInMillis){
             ToastnDialog.toastError(this.requireContext(),"Invalid date range")
          }else{
              val jsonContribution = HashMap<String,Any>()
              jsonContribution["enddate"] =  binding.txtFstatementEndDate.text.toString();
              jsonContribution["startdate"] =  binding.txtFstatementStartDate.text.toString();
              jsonContribution["groupid"] = DataUtil.selectedGrpId;
              jsonContribution["page"] = cPage
              jsonContribution["size"] = cSize
              groupViewModel.getGrpBadLoans(jsonContribution)
          }
      }
    }

    private fun initGroupContributionRV() {
        binding.rcvBadLoans.layoutManager= LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        loanApprovedAdapter = BadLoanAdapter(this,badLoanList)
        binding.rcvBadLoans.adapter = loanApprovedAdapter
    }

    private fun initData() {


        val jsonContribution = HashMap<String,Any>()
        jsonContribution["enddate"] = binding.txtFstatementEndDate.text.toString()
        jsonContribution["startdate"] = binding.txtFstatementStartDate.text.toString()
        jsonContribution["groupid"] = DataUtil.selectedGrpId;
        jsonContribution["page"] = cPage
        jsonContribution["size"] = cSize

        groupViewModel.getGrpBadLoans(jsonContribution)

        groupViewModel.myGroupApiResponseLD.observe(viewLifecycleOwner, Observer { myApiResponse ->
            CustomProgressBar.hide()
            Timber.v("myApiResponseLD  ${myApiResponse.code} ${myApiResponse.requestName} ${myApiResponse.message}")
            if(myApiResponse.code == 200){
                if(myApiResponse.requestName.contentEquals("getGrpBadLoanRequest")) {
                    badLoanList = myApiResponse.responseObj as ArrayList<LoanApproved>
                    Timber.v("grpTrxList ${badLoanList.size}")
                    if( badLoanList.isEmpty()){
                        binding.txtEmptyDesc.visibility = View.VISIBLE
                    }else{
                        binding.txtEmptyDesc.visibility = View.GONE
                    }
                    initGroupContributionRV()
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
