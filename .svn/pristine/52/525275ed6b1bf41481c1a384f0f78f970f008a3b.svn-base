package com.ekenya.echama.ui.group.approval


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.ekenya.echama.adapter.LoanRequestAdapter
import com.ekenya.echama.databinding.FragmentLoanRequestBinding
import com.ekenya.echama.model.Loan
import com.ekenya.echama.util.*
import com.ekenya.echama.viewModel.GroupViewModel

import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class LoanRequestFragment : Fragment() {
    private lateinit var loanList: ArrayList<Loan>
   // lateinit var rcvContribution : RecyclerView
    lateinit var groupViewModel : GroupViewModel
    lateinit var loanAdapter: LoanRequestAdapter
    private var _binding: FragmentLoanRequestBinding? = null
    private val binding get() = _binding!!
    val cPage = 0
    val cSize = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoanRequestBinding.inflate(inflater, container, false)
        val rootView = binding.root
        //val rootView= inflater.inflate(R.layout.fragment_group_contribution, container, false)
        groupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java)
      //  val btnCreateContribution = rootView.findViewById(R.id.btnCreate) as Button
      //  btnCreateContribution.setOnClickListener { findNavController().navigate(R.id.nav_create_contribution) }

        initData()
        initButton()
        return rootView
    }

    private fun initButton() {
    }

    private fun initGroupContributionRV() {
        binding.rcvLoanRequests.layoutManager= LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        loanAdapter = LoanRequestAdapter(this,loanList)
        binding.rcvLoanRequests.adapter = loanAdapter
    }

    private fun initData() {

        val jsonContribution = HashMap<String,Any>()
        jsonContribution["filterid"] = DataUtil.selectedGrpId
        jsonContribution["filter"] = "group"
        jsonContribution["page"] = cPage
        jsonContribution["size"] = cSize
        groupViewModel.getGrpPendingLoanRequest(jsonContribution)

        groupViewModel.myGroupApiResponseLD.observe(viewLifecycleOwner, Observer { myApiResponse ->
            CustomProgressBar.hide()
            Timber.v("myApiResponseLD  ${myApiResponse.code} ${myApiResponse.requestName} ${myApiResponse.message}")
            if(myApiResponse.code == 200){
                if(myApiResponse.requestName.contentEquals("getGrpPendingLoanRequest")) {
                    val gtrx = myApiResponse.responseObj as ArrayList<Loan>
                    Timber.v("grpTrxList ${gtrx.size}")
                    if( gtrx.isEmpty()){
                        binding.txtEmptyDesc.visibility = View.VISIBLE
                    }else{
                        loanList = gtrx
                        initGroupContributionRV()
                        binding.txtEmptyDesc.visibility = View.GONE
                    }
                }
                if(myApiResponse.requestName.contentEquals("approveLoanRequest")){
                    successAlert("Success",myApiResponse.message,false)
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
