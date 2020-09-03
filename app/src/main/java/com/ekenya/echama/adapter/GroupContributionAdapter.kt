package com.ekenya.echama.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ekenya.echama.R
import com.ekenya.echama.model.AllContributionModel
import com.ekenya.echama.model.Contribution
import kotlinx.android.synthetic.main.layout_active_contribution.view.*

class GroupContributionAdapter(val context:Fragment,val contribList:List<Contribution>):RecyclerView.Adapter<GroupContributionAdapter.GroupContViewHolder>(){

    inner class GroupContViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        val tvContributionName = itemView.tvContributionName
        val tvContributionAmount = itemView.tvContributionAmount
        val tvContributionType = itemView.tvContributionType
        val tvContributionDueDate = itemView.tvContributionDueDate
        val tvContributionFrequecy = itemView.tvContributionFrequecy
        val tvContributedNumber = itemView.tvContributedNumber

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupContViewHolder {
        return GroupContViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_active_contribution,parent,false))
    }

    override fun getItemCount(): Int {
       return contribList.size
    }

    override fun onBindViewHolder(holder: GroupContViewHolder, position: Int) {
        holder.tvContributionAmount.text = contribList[position].getFContributedAmount()+"/-KES"
        holder.tvContributionDueDate.text = contribList[position].startdate
        holder.tvContributionFrequecy.text = contribList[position].scheduletypename
        holder.tvContributionType.text = contribList[position].contributiontypename
        holder.tvContributionName.text = contribList[position].name

        holder.itemView.setOnClickListener {
            val bundle =  Bundle()
            bundle.putString("contribution",contribList[position].toJson())
            context.findNavController().navigate(R.id.nav_single_contribution,bundle) }
    }

}