package com.ekenya.echama.ui.group.wallet


import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.ekenya.echama.R
import com.ekenya.echama.databinding.FragmentPaymentBinding
import com.ekenya.echama.databinding.FragmentPenaltyBinding
import com.ekenya.echama.util.CustomProgressBar
import com.ekenya.echama.util.DataUtil
import com.ekenya.echama.util.ToastnDialog
import com.ekenya.echama.util.successAlert
import com.ekenya.echama.viewModel.GroupViewModel
import com.ekenya.echama.viewModel.MainViewModel
import timber.log.Timber
import java.util.regex.Pattern

/**
 * A simple [Fragment] subclass.
 */
class PaymentFragment : Fragment() {
    private var paymentmode: String = ""
    private var amount: Double = 0.0
    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel
    var action: String = "" 

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       // val rootview= inflater.inflate(R.layout.fragment_payment, container, false)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        val rootView = binding.root
        //action = "penalty"
       // amount = 10.00
        arguments?.getString("action")?.let {
            action = it
            //penalty should contain contribution id
            //wallet recharge wallet
            //contribution contribution payment
        }
        arguments?.getString("amount")?.let {
            amount = it.toDouble()
        }
        
        initButton()
        initData()
        initView()
        initEdittextView()
        initRadioBtn()
        return rootView
    }

    private fun initRadioBtn() {
        binding.rbWallet.setOnClickListener{
            binding.rbMobileMoney.isChecked = false
            binding.rbCard.isChecked = false
            paymentmode = "wallet"
        }
        binding.rbMobileMoney.setOnClickListener{
            binding.rbWallet.isChecked = false
            binding.rbCard.isChecked = false
            paymentmode = "mobile"
        }
        binding.rbCard.setOnClickListener{
            binding.rbWallet.isChecked = false
            binding.rbMobileMoney.isChecked = false
            paymentmode = "card"
        }



    }

    private fun initEdittextView() {

        binding.etxtAmount.isFocusableInTouchMode = false
        binding.etxtAmount.isFocusable = false
        binding.etxtAmount.setOnClickListener {
            binding.etxtAmount.isFocusableInTouchMode = true
            binding.etxtAmount.isFocusable = true
        }

        binding.etxtAmount.doOnTextChanged { text, start, count, after ->
            text?.let {
                if(!it.isEmpty()){
                    binding.tlAmount.hint = binding.etxtAmount.hint
                }else{
                    binding.tlAmount.hint = null
                    binding.etxtAmount.hint = "${DataUtil.gcurrency} ${amount}"
                }
            }
        }
        binding.etxtAmount.hint = "${DataUtil.gcurrency} ${amount}"
        binding.etxtAmount.clearFocus()

    }

    private fun initView() {
        binding.cardCL.visibility = View.INVISIBLE
        if(action.contentEquals("wallet")){
            binding.txtDesc.text = "Enter amount you want to recharge your wallet"
            binding.walletCL.visibility = View.GONE
        }
        if(action.contentEquals("penalty")){
            binding.txtDesc.text = resources.getString(R.string.you_are_on_track_amount_payable)
            binding.txtPenaltyDesc.visibility = View.VISIBLE
            binding.txtAmountDesc.visibility = View.GONE
            binding.txtPenaltyDesc.text = "Penalty: ${DataUtil.gcurrency} ${amount}"
        }
        if(action.contentEquals("contribution")){
            binding.txtAmountDesc.visibility = View.VISIBLE
            binding.txtAmountDesc.text = "Member Contribution: ${DataUtil.gcurrency} ${amount}"
            binding.txtPenaltyDesc.visibility = View.GONE
        }

        if(amount == 0.00){
            binding.txtAmountDesc.visibility = View.GONE
            binding.txtPenaltyDesc.visibility = View.GONE
        }
    }

    private fun initData() {

        mainViewModel.myApiResponseLD.observe(viewLifecycleOwner, Observer { myApiResponse ->
            CustomProgressBar.hide()
            if(myApiResponse.code == 200){
                if (myApiResponse.requestName.contentEquals("makePaymentRequest")){
                    successAlert("Success",myApiResponse.message)
                }
            }else{
                Timber.v("Error: "+myApiResponse.requestName+" "+myApiResponse.message)
                ToastnDialog.toastError(this.context!!,"Error:"+myApiResponse.message)
            }
        })
    }

    private fun initButton() {
        binding.btnPay.setOnClickListener { 
            if(validate_info()){
                    val jsonGroupDetails = HashMap<String,Any>()
                    jsonGroupDetails["amount"] = amount
                    jsonGroupDetails["paymentmode"] = paymentmode
                    mainViewModel.makePayment(jsonGroupDetails)
                    CustomProgressBar.show(this.requireContext(), "Sending request")
            }
        }
    }

    
    private fun validate_info(): Boolean {
        if(paymentmode.isEmpty()){
            ToastnDialog.toastError(this.requireContext(),"Kindly choose your payment mode")
            return false
        }
        if(binding.etxtAmount.text.isEmpty()){
            binding.etxtAmount.setText( amount.toString())
        }
        return true
    }


}
