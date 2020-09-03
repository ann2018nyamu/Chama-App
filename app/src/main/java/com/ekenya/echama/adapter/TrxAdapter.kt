package com.ekenya.echama.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.ekenya.echama.R
import com.ekenya.echama.model.Transaction

class TrxAdapter(val context: Fragment, val activityList:List<Transaction>):RecyclerView.Adapter<TrxAdapter.RecentActivityViewHolder> (){
    inner class RecentActivityViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        //val regionTextview = itemView.findViewById(R.id.tvregionName) as TextView
        val txtGroupName = itemView.findViewById(R.id.txtGroupName) as TextView
        val txtGroupContributionName = itemView.findViewById(R.id.txtGroupContributionName) as TextView
        val txtAmount = itemView.findViewById(R.id.txtAmount) as TextView
        val txtTimestamp = itemView.findViewById(R.id.txtTimestamp) as TextView
        val imgGroupProfileHolder = itemView.findViewById(R.id.imgGroupProfileHolder) as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentActivityViewHolder {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return RecentActivityViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_recent_activity,parent,false))
    }

    override fun getItemCount(): Int {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return activityList.size
    }

    override fun onBindViewHolder(holder: RecentActivityViewHolder, position: Int) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        holder.txtGroupName.text = activityList[position].groupname
        holder.txtGroupContributionName.text = activityList[position].contributionname
        holder.txtAmount.text = activityList[position].getFAmount()+" \n"+activityList[position].currency

        holder.txtTimestamp.text = activityList[position].getFTrxDate()
//        Glide.with(context)
//            .asBitmap()
//            .load(activityList[position].imageUrl)
//            .into(holder.imgGroupProfileHolder)

    }


}