package com.ekenya.echama.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ekenya.echama.R
import com.ekenya.echama.model.Loan
import com.ekenya.echama.model.LoanProduct

class LoansAdapter(val context: Fragment, val loanList:List<Loan>):RecyclerView.Adapter<LoansAdapter.RecentActivityViewHolder> (){
    inner class RecentActivityViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        //val regionTextview = itemView.findViewById(R.id.tvregionName) as TextView
        val tvLoanProductName = itemView.findViewById(R.id.tvLoanProductName) as TextView
        val tvLoaneeName = itemView.findViewById(R.id.tvLoaneeName) as TextView
        val tvAmount = itemView.findViewById(R.id.tvAmount) as TextView
        val tvDisbursedDate = itemView.findViewById(R.id.tvDisbursedDate) as TextView
        val tvBalanceAmount = itemView.findViewById(R.id.tvBalanceAmount) as TextView
        val rootCl = itemView.findViewById(R.id.rootCl) as ConstraintLayout
        val btnItemRecordPayment = itemView.findViewById(R.id.btnItemRecordPayment) as Button
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentActivityViewHolder {
        return RecentActivityViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.items_loans,parent,false))
    }

    override fun getItemCount(): Int {
        return loanList.size
    }

    override fun onBindViewHolder(holder: RecentActivityViewHolder, position: Int) {
        holder.tvLoanProductName.text = loanList[position].loanProductName
        holder.tvLoaneeName.text = loanList[position].memberName
        holder.tvAmount.text = loanList[position].getFAmount()
        holder.tvBalanceAmount.text = loanList[position].getFbalanceAmount()
        holder.tvDisbursedDate.text = loanList[position].disbursedDate

        holder.btnItemRecordPayment.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("loan",loanList[position].toJson())
            bundle.putString("action","loan")
            findNavController(context).navigate(R.id.nav_record_payment,bundle)
        }

        holder.rootCl.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("loan",loanList[position].toJson())
            findNavController(context).navigate(R.id.nav_loanpayment,bundle)
        }

    }


}