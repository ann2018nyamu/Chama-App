package com.ekenya.echama.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ekenya.echama.R
import com.ekenya.echama.inc.AppConstants
import com.ekenya.echama.model.Loan
import com.ekenya.echama.model.LoanPayment
import com.ekenya.echama.model.LoanProduct
import com.ekenya.echama.model.Payment
import com.mikhaellopez.circularimageview.CircularImageView

class LoanPaymentAdapter(val context: Fragment, val loanPaymentList:List<LoanPayment>):RecyclerView.Adapter<LoanPaymentAdapter.RecentActivityViewHolder> (){
    inner class RecentActivityViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        //val regionTextview = itemView.findViewById(R.id.tvregionName) as TextView
        val tvMemberName = itemView.findViewById(R.id.tvMemberName) as TextView
        val tvProductName = itemView.findViewById(R.id.tvProductName) as TextView
        val tvAmount = itemView.findViewById(R.id.tvAmount) as TextView
        val tvReceiptNo = itemView.findViewById(R.id.tvReceiptNo) as TextView
        val tvBalanceAmount = itemView.findViewById(R.id.tvBalanceAmount) as TextView
        val ivGroupProfileHolder = itemView.findViewById(R.id.ivGroupProfileHolder) as CircularImageView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentActivityViewHolder {
        return RecentActivityViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.items_loanpayment,parent,false))
    }

    override fun getItemCount(): Int {
        return loanPaymentList.size
    }

    override fun onBindViewHolder(holder: RecentActivityViewHolder, position: Int) {
        holder.tvMemberName.text = loanPaymentList[position].memberName
        holder.tvProductName.text = loanPaymentList[position].loanProductName
        holder.tvAmount.text = "Paid:"+loanPaymentList[position].getFPaidAmount()
        holder.tvBalanceAmount.text = "Balance:"+loanPaymentList[position].getFBalanceAmount()
        holder.tvReceiptNo.text = "Receipt: "+loanPaymentList[position].receiptnumber

        Glide.with(context.requireContext())
            .asBitmap()
            .fitCenter()
            .load(AppConstants.base_url+"/chama/mobile/req/countries/"+"KE")
            .into( holder.ivGroupProfileHolder)
    }


}