package com.ekenya.echama.adapter

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ekenya.echama.R
import com.ekenya.echama.model.Loan
import com.ekenya.echama.model.LoanProduct
import com.ekenya.echama.model.Payment
import com.ekenya.echama.repository.MyApiResponse
import com.ekenya.echama.viewModel.GroupViewModel

class PaymentRequestAdapter(val context: Fragment, val paymentList:ArrayList<Payment>):RecyclerView.Adapter<PaymentRequestAdapter.RecentActivityViewHolder> (){
    val groupModel = ViewModelProvider(context).get(GroupViewModel::class.java)
    inner class RecentActivityViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        //val regionTextview = itemView.findViewById(R.id.tvregionName) as TextView
        val tvAccountName = itemView.findViewById(R.id.tvAccountName) as TextView
        val tvAccountType = itemView.findViewById(R.id.tvAccountType) as TextView
        val tvAmount = itemView.findViewById(R.id.tvAmount) as TextView
        val tvAccountDesc = itemView.findViewById(R.id.tvAccountDesc) as TextView
        val btnItemApprove = itemView.findViewById(R.id.btnItemApprove) as Button
        val btnItemReject = itemView.findViewById(R.id.btnItemReject) as Button
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentActivityViewHolder {
        return RecentActivityViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.items_payment,parent,false))
    }

    override fun getItemCount(): Int {
        return paymentList.size
    }

    override fun onBindViewHolder(holder: RecentActivityViewHolder, position: Int) {
        holder.tvAccountName.text = paymentList[position].creditaccountname
        holder.tvAccountType.text = paymentList[position].creditaccounttype
        holder.tvAmount.text = paymentList[position].getFAmount()
        holder.tvAccountDesc.text = paymentList[position].narration

        holder.btnItemReject.setOnClickListener {
            showActionDialogue("reject",position)
        }
        holder.btnItemApprove.setOnClickListener {
            showActionDialogue("approve",position)
        }

    }
    fun showActionDialogue(action:String,position: Int){
        var title = "Approve"
        var msg = "Do you want to approve payment"
        if(action.contentEquals("decline")){
            title = "Reject"
            msg = "Do you want to reject payment"
        }
        val builder = AlertDialog.Builder(context.requireContext())
        //set title for alert dialog
        builder.setTitle(title)
        //set message for alert dialog
        builder.setMessage(msg)
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Yes"){dialogInterface, which ->
            groupModel.myGroupApiResponseLD.postValue(MyApiResponse(700,"acceptDeclineGrpPayment","",""))

            val jsonDeclineDetails = HashMap<String,Any>()
            jsonDeclineDetails["filterid"] =  paymentList[position].paymentid
            if(action.contentEquals("approve")){
                jsonDeclineDetails["filter"] = "approve"
                groupModel.approveDeclineGrpPaymentRequest(jsonDeclineDetails)
            }else{
                jsonDeclineDetails["filter"] = "decline"
                groupModel.approveDeclineGrpPaymentRequest(jsonDeclineDetails)
            }
            paymentList.remove(paymentList[position])
            notifyItemRangeChanged(position, itemCount -1)
            notifyDataSetChanged()
            dialogInterface.dismiss()
        }
        //performing negative action
        builder.setNegativeButton("No"){dialogInterface, which ->
            dialogInterface.dismiss()
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }




}