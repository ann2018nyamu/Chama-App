package com.ekenya.echama.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.ekenya.echama.R
import com.ekenya.echama.model.AllMembersModel
import kotlinx.android.synthetic.main.layout_select_official.view.*

class SelectOfficialAdapter (context:Fragment,val memberList: List<AllMembersModel.Member>,val roleId:Int,val roleName:String):RecyclerView.Adapter<SelectOfficialAdapter.SelectViewHolder>(){

    inner class SelectViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val tvMemberName=itemView.tvMemberName
        val tvPhoneNumber=itemView.tvPhoneNumber
        val btnRole=itemView.btnRole
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectViewHolder {
        return SelectViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_select_official,parent,false))
    }

    override fun getItemCount(): Int {
       return memberList.size
    }

    override fun onBindViewHolder(holder: SelectViewHolder, position: Int) {
        holder.tvMemberName.setText(memberList.get(position).firstName)
        holder.tvPhoneNumber.setText(memberList.get(position).phoneNumber)
    }
}