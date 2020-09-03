package com.ekenya.echama.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ekenya.echama.R
import com.ekenya.echama.model.AllGroupsModel
import com.ekenya.echama.model.Group
import com.ekenya.echama.util.DataUtil
import kotlinx.android.synthetic.main.layout_all_groups.view.*

class AllGroupsAdapter(val context:Fragment,val groupList:List<Group>):RecyclerView.Adapter<AllGroupsAdapter.AllGroupViewHolder>()
{

    inner class AllGroupViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val groupName = itemView.tvNewGroup
        val participants = itemView.tvNewGroupDesc
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllGroupViewHolder {
        return AllGroupViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_all_groups,parent,false))
    }

    override fun getItemCount(): Int {
        return groupList.size
    }

    override fun onBindViewHolder(holder: AllGroupViewHolder, position: Int) {
        val  bundle=Bundle()
        bundle.putString("group", groupList[position].toJson())

        holder.groupName.setText(groupList[position].groupname)
        holder.participants.text = "${groupList[position].groupsize} Participants"

        holder.itemView.setOnClickListener {
            DataUtil.selectedGrpId = groupList[position].groupid
            DataUtil.currentGroup = groupList[position]
            context.findNavController().navigate(R.id.nav_group_details,bundle)
        }
    }
}