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
import com.ekenya.echama.adapter.LoanPaymentRequestAdapter

import com.ekenya.echama.adapter.LoanRequestAdapter
import com.ekenya.echama.adapter.PaymentRequestAdapter
import com.ekenya.echama.databinding.*
import com.ekenya.echama.model.Loan
import com.ekenya.echama.model.Payment
import com.ekenya.echama.util.*
import com.ekenya.echama.viewModel.GroupViewModel

import timber.log.Timber

/**
 * this class shows the loan payment transaction of a specific loan
 */
class LoanPaymentRequestFragment : Fragment() {
    private lateinit var paymentList: ArrayList<Payment>
    lateinit var groupViewModel : GroupViewModel
    lateinit var paymentAdapter: LoanPaymentRequestAdapter
    private var _binding: FragmentGloanPaymentRequestsBinding? = null
    private val binding get() = _binding!!
    val cPage = 0
    val cSize = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGloanPaymentRequestsBinding.inflate(inflater, container, false)
        val rootView = binding.root
        groupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java)

        initData()
        initButton()
        return rootView
    }

    private fun initButton() {
    }

    private fun initGrpLoanPaymentRV() {
        binding.rcvPayments.layoutManager= LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        paymentAdapter = LoanPaymentRequestAdapter(this,paymentList)
        binding.rcvPayments.adapter = paymentAdapter
    }

    private fun initData() {
        val jsonContribution = HashMap<String,Any>()
        jsonContribution["filterid"] = DataUtil.selectedGrpId
        jsonContribution["filter"] = "group"
        groupViewModel.getGrpLoanPaymentRequest(jsonContribution)

        groupViewModel.myGroupApiResponseLD.observe(viewLifecycleOwner, Observer { myApiResponse ->
            CustomProgressBar.hide()
            Timber.v("myApiResponseLD  ${myApiResponse.code} ${myApiResponse.requestName} ${myApiResponse.message}")
            if(myApiResponse.code == 200){
                if(myApiResponse.requestName.contentEquals("getGrpLoanPaymentRequest")) {

                    val gtrx = myApiResponse.responseObj as ArrayList<Payment>
                    Timber.v("grpPaymentList ${gtrx.size}")
                    if( gtrx.isEmpty()){
                        binding.txtEmptyDesc.visibility = View.VISIBLE
                    }else{
                        paymentList = gtrx
                        initGrpLoanPaymentRV()
                        binding.txtEmptyDesc.visibility = View.GONE
                    }
                }
                if(myApiResponse.requestName.contentEquals("approveDeclineGrpPaymentRequest")){
                        successAlert("Success",myApiResponse.message,false)
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
