package com.ekenya.echama.ui.group.loan


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.ekenya.echama.R
import com.ekenya.echama.adapter.GroupContributionAdapter
import com.ekenya.echama.adapter.LoanPaymentAdapter
import com.ekenya.echama.databinding.FragmentGroupContributionBinding
import com.ekenya.echama.databinding.FragmentGroupWalletBinding
import com.ekenya.echama.databinding.FragmentLoanPaymentBinding
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

/**
 * A simple [Fragment] subclass.
 */
class LoanPaymentFragment : Fragment() {
    private  var loanPaymentList: ArrayList<LoanPayment> = ArrayList()
   // lateinit var rcvContribution : RecyclerView
    lateinit var groupViewModel : GroupViewModel
    lateinit var loanAdapter:LoanPaymentAdapter
    private var _binding: FragmentLoanPaymentBinding? = null

    private val binding get() = _binding!!
    val cPage = 0
    val cSize = 100
    var currentApprovedloan = LoanApproved()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoanPaymentBinding.inflate(inflater, container, false)
        val rootView = binding.root
        //val rootView= inflater.inflate(R.layout.fragment_group_contribution, container, false)
        groupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java)
      //  val btnCreateContribution = rootView.findViewById(R.id.btnCreate) as Button
      //  btnCreateContribution.setOnClickListener { findNavController().navigate(R.id.nav_create_contribution) }
//        val lstr = "{\"loanproductid\":1,\"loanapplicationid\":1,\"amount\":100.0,\"loanproductname\":\"Emergency Loan\",\"appliedon\":1587728773000,\"membername\":\"muriithi Wycliffs\",\"memberphonenumber\":\"254715702887\",\"unpaidloans\":0,\"reminder\":{\"note\":\"\",\"applied\":true,\"enddate\":{\"hour\":\"14\",\"month\":\"3\",\"minute\":\"46\",\"dayofweek\":\"*\",\"dayofmonth\":\"28\"},\"frequency\":{\"hour\":\"14\",\"month\":\"*\",\"minute\":\"45\",\"dayofweek\":\"0\",\"dayofmonth\":\"*\"},\"startdate\":{\"hour\":\"14\",\"month\":\"3\",\"minute\":\"45\",\"dayofweek\":\"*\",\"dayofmonth\":\"24\"}}}";
//        currentloan = currentloan.fromJson(lstr)
        arguments?.getString("loan")?.let {
            currentApprovedloan = currentApprovedloan.fromJson(it)
        }

        initData()
        initButton()
        return rootView
    }

    private fun initButton() {

    }

    private fun initGroupContributionRV() {
        binding.rcvLoanPayment.layoutManager= LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        loanAdapter = LoanPaymentAdapter(this,loanPaymentList)
        binding.rcvLoanPayment.adapter = loanAdapter
    }

    private fun initData() {

        val jsonContribution = HashMap<String,Any>()
//        jsonContribution.put("filterid", DataUtil.selectedGrpId)
//        jsonContribution.put("filter", "group") //Todo change this to specific loan
        jsonContribution["filterid"] = currentApprovedloan.loanId
        jsonContribution["filter"] = "disbursedloan" //Todo change this to specific loan
        jsonContribution["page"] = cPage
        jsonContribution["size"] = cSize

        groupViewModel.getLoanPayment(jsonContribution)

        groupViewModel.myGroupApiResponseLD.observe(viewLifecycleOwner, Observer { myApiResponse ->
            CustomProgressBar.hide()
            Timber.v("myApiResponseLD  ${myApiResponse.code} ${myApiResponse.requestName} ${myApiResponse.message}")
            if(myApiResponse.code == 200){
                if(myApiResponse.requestName.contentEquals("getLoanPaymentRequest")) {
                     loanPaymentList = myApiResponse.responseObj as ArrayList<LoanPayment>
                    Timber.v("loan payment List ${loanPaymentList.size}")
                    if( loanPaymentList.isEmpty()){
                        binding.txtEmptyDesc.visibility = View.VISIBLE
                    }else{
                        initGroupContributionRV()
                        binding.txtEmptyDesc.visibility = View.GONE
                    }
                }
//                if(myApiResponse.requestName.contentEquals("getGrpTrxRequest")){
//
//                }
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
