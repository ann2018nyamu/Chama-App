package com.ekenya.echama.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ekenya.echama.R
import com.ekenya.echama.model.LoanProduct
import com.ekenya.echama.util.expiredTokenDialogue
import com.ekenya.echama.util.getHashedPin256
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class LoanProductAdapter(val context: Fragment, val loanProductList:List<LoanProduct>):RecyclerView.Adapter<LoanProductAdapter.RecentActivityViewHolder> (){
    inner class RecentActivityViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        //val regionTextview = itemView.findViewById(R.id.tvregionName) as TextView
        val tvLoanProductName = itemView.findViewById(R.id.tvLoanProductName) as TextView
        val tvLoanProductDesc = itemView.findViewById(R.id.tvLoanProductDesc) as TextView
        val tvMinMaxAmount = itemView.findViewById(R.id.tvMinMaxAmount) as TextView
        val tvInterestPeriod = itemView.findViewById(R.id.tvInterestPeriod) as TextView
        val tvInterestRate = itemView.findViewById(R.id.tvInterestRate) as TextView
        val btnItemApplyLoan = itemView.findViewById(R.id.btnItemApplyLoan) as Button
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentActivityViewHolder {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return RecentActivityViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_loan_product,parent,false))
    }

    override fun getItemCount(): Int {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return loanProductList.size
    }

    override fun onBindViewHolder(holder: RecentActivityViewHolder, position: Int) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        holder.tvLoanProductName.text = loanProductList[position].loanProductName
        holder.tvLoanProductDesc.text = loanProductList[position].loanProductDesc
        holder.tvMinMaxAmount.text = "Loan limit:\n"+loanProductList[position].getFMinumAmount()+" - "+loanProductList[position].getFMaximumAmount()
        holder.tvInterestRate.text = "Interest type:\n"+loanProductList[position].interestType+": "+loanProductList[position].interestRate
        holder.tvInterestPeriod.text = "Duration: \n"+loanProductList[position].period.toInt()+" "+loanProductList[position].paymentperiodtype  //Todo put any test here
        holder.btnItemApplyLoan.setOnClickListener {
           val bundle = Bundle()
            bundle.putString("loanproduct",loanProductList[position].toJson())
            findNavController(context).navigate(R.id.nav_applyloan,bundle)
        }

    }


}