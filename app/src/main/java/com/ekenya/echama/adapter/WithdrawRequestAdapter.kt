package com.ekenya.echama.adapter

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ekenya.echama.R
import com.ekenya.echama.inc.AppConstants
import com.ekenya.echama.model.Withdraw
import com.ekenya.echama.repository.MyApiResponse
import com.ekenya.echama.viewModel.GroupViewModel

class WithdrawRequestAdapter(val context: Fragment, val withdrawList:ArrayList<Withdraw>):RecyclerView.Adapter<WithdrawRequestAdapter.RecentActivityViewHolder> (){
    val groupModel = ViewModelProvider(context).get(GroupViewModel::class.java)
    inner class RecentActivityViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        //val regionTextview = itemView.findViewById(R.id.tvregionName) as TextView
        val tvAccountName = itemView.findViewById(R.id.tvAccountName) as TextView
        val tvAccountType = itemView.findViewById(R.id.tvAccountType) as TextView
        val tvAmount = itemView.findViewById(R.id.tvAmount) as TextView
        val tvAccountDesc = itemView.findViewById(R.id.tvAccountDesc) as TextView
        val tvReason = itemView.findViewById(R.id.tvReason) as TextView
        val btnItemApprove = itemView.findViewById(R.id.btnItemApprove) as Button
        val btnItemReject = itemView.findViewById(R.id.btnItemReject) as Button
        val ivGroupProfileHolder = itemView.findViewById(R.id.ivGroupProfileHolder) as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentActivityViewHolder {
        return RecentActivityViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_withdraw,parent,false))
    }

    override fun getItemCount(): Int {
        return withdrawList.size
    }

    override fun onBindViewHolder(holder: RecentActivityViewHolder, position: Int) {
        holder.tvAccountName.text = withdrawList[position].debitaccountname
        holder.tvAccountType.text = withdrawList[position].debitaccounttype
        holder.tvAmount.text = withdrawList[position].getFAmount()
        holder.tvAccountDesc.text = withdrawList[position].narration
        holder.tvReason.text = withdrawList[position].reason

        holder.btnItemReject.setOnClickListener {
            showActionDialogue("decline",position)
        }
        holder.btnItemApprove.setOnClickListener {
            showActionDialogue("approve",position)
        }
        Glide.with(context.requireContext())
            .asBitmap()
            .load(AppConstants.base_url+"/chama/mobile/req/countries/"+"KE")
            .into( holder.ivGroupProfileHolder)

    }
    fun showActionDialogue(action:String,position: Int){
        var title = "Approve"
        var msg = "Do you want to approve withdrawal"
        if(action.contentEquals("decline")){
            title = "Reject"
            msg = "Do you want to reject withdrawal"
        }
        val builder = AlertDialog.Builder(context.requireContext())
        //set title for alert dialog
        builder.setTitle(title)
        //set message for alert dialog
        builder.setMessage(msg)
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Yes"){dialogInterface, which ->
            groupModel.myGroupApiResponseLD.postValue(MyApiResponse(700,"acceptDeclineGrpWithdrawal","",""))

            val jsonDeclineDetails = HashMap<String,Any>()
            jsonDeclineDetails["filterid"] =  withdrawList[position].withdrawId
            if(action.contentEquals("approve")){
                jsonDeclineDetails["filter"] = "approve"
                groupModel.approveDeclineGrpWithdrawal(jsonDeclineDetails)
            }else{
                jsonDeclineDetails["filter"] = "decline"
                groupModel.approveDeclineGrpWithdrawal(jsonDeclineDetails)
            }
            withdrawList.remove(withdrawList[position])
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