package com.ekenya.echama.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.ekenya.echama.R
import com.ekenya.echama.model.AllMembersModel
import kotlinx.android.synthetic.main.item_gmember_contribution.view.*

//Todo remove or edit this adapter
class GroupTrxAdapter (val context: Fragment, val memberList:List<AllMembersModel.Member>):RecyclerView.Adapter<GroupTrxAdapter.AllMembersViewHolder>(){

    inner class AllMembersViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val membername = itemView.tvNewGroup
        val phonenumber = itemView.tvNewGroupDesc
        val amount = itemView.tvAmount
        val currency = itemView.tvCurrency
        val paymentdate = itemView.tvPaymentDate

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllMembersViewHolder {
        return AllMembersViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_gmember_contribution,parent,false))
    }

    override fun getItemCount(): Int {
      return memberList.size
    }

    override fun onBindViewHolder(holder: AllMembersViewHolder, position: Int) {
       // val lastname? = memberList.get(position)?.lastName? :null
        holder.currency.setText("Ksh")
        holder.phonenumber.setText(memberList[position].phoneNumber)
        holder.membername.setText(memberList.get(position).firstName+" "+memberList.get(position).lastName)//TODO REMOVE IF LAST NAME IS NULL
        holder.amount.setText(memberList.get(position).totalContribution.toString()+".00")
        holder.paymentdate.setText(memberList.get(position).createdOn)
    }
}