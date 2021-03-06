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
import com.ekenya.echama.adapter.LoanApprovedAdapter
import com.ekenya.echama.databinding.FragmentGroupContributionBinding
import com.ekenya.echama.databinding.FragmentGroupWalletBinding
import com.ekenya.echama.databinding.FragmentLoansBinding
import com.ekenya.echama.databinding.FragmentMyloansBinding
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
class MyLoansFragment : Fragment() {
    private lateinit var loanApprovedList: ArrayList<LoanApproved>
    lateinit var groupViewModel : GroupViewModel
    lateinit var loanAdapter:LoanApprovedAdapter
    private var _binding: FragmentMyloansBinding? = null

    private val binding get() = _binding!!
    val cPage = 0
    val cSize = 100
    var currentUser = User("")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyloansBinding.inflate(inflater, container, false)
        val rootView = binding.root

        groupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java)
        arguments?.getString("user").let {
            currentUser.fromJson(it.toString())
            Timber.v(currentUser.toJson())
        }

        initData()
        initButton()
        return rootView
    }

    private fun initButton() {

    }

    private fun initGroupContributionRV() {
        binding.rcvMyLoans.layoutManager= LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        loanAdapter = LoanApprovedAdapter(this,loanApprovedList)
        binding.rcvMyLoans.adapter = loanAdapter
    }

    private fun initData() {

        val jsonContribution = HashMap<String,Any>()
        jsonContribution.put("filterid", "")
        jsonContribution["filter"] = "user"
        jsonContribution["membername"] = currentUser.phonenumber
        jsonContribution["page"] = cPage
        jsonContribution["size"] = cSize

        groupViewModel.getGrpLoans(jsonContribution)?.observe(viewLifecycleOwner, Observer {
            Timber.v("getGroupContribution size ${it.size}")
            if(it.isNotEmpty()){
                loanApprovedList = it as ArrayList<LoanApproved>
                initGroupContributionRV()
                binding.txtEmptyDesc.visibility = View.GONE
            }else{
                binding.txtEmptyDesc.visibility = View.VISIBLE
            }
        })

        groupViewModel.myGroupApiResponseLD.observe(viewLifecycleOwner, Observer { myApiResponse ->
            CustomProgressBar.hide()
            Timber.v("myApiResponseLD  ${myApiResponse.code} ${myApiResponse.requestName} ${myApiResponse.message}")
            if(myApiResponse.code == 200){
                if(myApiResponse.requestName.contentEquals("getGrpLoanRequest")) {
                    val gtrx = myApiResponse.responseObj as List<Loan>
                    Timber.v("grpTrxList ${gtrx.size}")
                    if( gtrx.isEmpty()){
                        binding.txtEmptyDesc.visibility = View.VISIBLE
                    }else{
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
