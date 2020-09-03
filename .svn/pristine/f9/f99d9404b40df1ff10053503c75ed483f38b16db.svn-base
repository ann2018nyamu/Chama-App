package com.ekenya.echama.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ekenya.echama.R
import com.ekenya.echama.model.Transaction
import kotlinx.android.synthetic.main.layout_contribution_single_payment.view.*
import timber.log.Timber

class ContributionDetailsAdapter (context: Fragment,val activityList:List<Transaction>):RecyclerView.Adapter<ContributionDetailsAdapter.ContribViewHolder>(){

    inner class ContribViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val tvMemberName = itemView.tvMemberName
        val tvDate  = itemView.tvDate
        val tvAmount = itemView.tvAmount
        val tvCurrency = itemView.tvCurrency
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContribViewHolder {
        return ContribViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_contribution_single_payment,parent,false))
    }

    override fun getItemCount(): Int {
        Timber.v(""+activityList.size)
       return activityList.size
    }

    override fun onBindViewHolder(holder: ContribViewHolder, position: Int) {

        holder.tvMemberName.text = activityList[position].membername.capitalize()
        holder.tvDate.text = activityList[position].getFTrxDate()
        holder.tvAmount.text = activityList[position].getFAmount()
        holder.tvCurrency.text = activityList[position].currency
    }
}