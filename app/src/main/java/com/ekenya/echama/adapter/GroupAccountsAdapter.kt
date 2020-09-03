package com.ekenya.echama.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.ekenya.echama.R
import com.ekenya.echama.model.GroupAccount
import com.ekenya.echama.model.Transaction
import timber.log.Timber

class GroupAccountsAdapter(val context: Fragment, val activityList:List<GroupAccount>):RecyclerView.Adapter<GroupAccountsAdapter.GroupAccountsViewHolder> (){
    inner class GroupAccountsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        //val regionTextview = itemView.findViewById(R.id.tvregionName) as TextView
        val tvAccountName = itemView.findViewById(R.id.tvAccountName) as TextView
        val tvAccountBalance = itemView.findViewById(R.id.tvAccountBalance) as TextView
        val tvAccountNumber = itemView.findViewById(R.id.tvAccountNumber) as TextView
        val tvAccountDesc = itemView.findViewById(R.id.tvAccountDesc) as TextView
        val imgActionDelete = itemView.findViewById(R.id.imgActionDelete) as ImageView
        val imgActionEdit = itemView.findViewById(R.id.imgActionEdit) as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupAccountsViewHolder {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return GroupAccountsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_group_account,parent,false))
    }

    override fun getItemCount(): Int {
        return activityList.size
    }

    override fun onBindViewHolder(holder: GroupAccountsViewHolder, position: Int) {
        holder.tvAccountName.text = activityList[position].accountname
        holder.tvAccountBalance.text = activityList[position].getFBalance()
        holder.tvAccountNumber.text = "Acc No."+activityList[position].accountdetails?.accountNumber
        holder.tvAccountDesc.text = activityList[position].accountdetails?.getNameNBranch()
        holder.imgActionDelete.visibility = View.GONE
        holder.imgActionEdit.visibility = View.GONE
    }


}