package com.ekenya.echama.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ekenya.echama.R
import com.ekenya.echama.ui.register.RegionCallback
import com.ekenya.echama.ui.register.RegisterStepTwo
import com.ekenya.echama.util.DataUtil

class RegionAdapter (val regionList:List<RegionCallback>,val context:Fragment):RecyclerView.Adapter<RegionAdapter.RegionViewHolder>(){
     inner class RegionViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
         //val regionTextview = itemView.findViewById(R.id.tvregionName) as TextView
         val regionTextview = itemView.findViewById(R.id.tvCountryName) as TextView
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionViewHolder {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return RegionViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_region,parent,false))
    }

    override fun getItemCount(): Int {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return regionList.size
    }

    override fun onBindViewHolder(holder: RegionViewHolder, position: Int) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        holder.regionTextview.setText(regionList.get(position).name)
        holder.itemView.setOnClickListener {
            RegisterStepTwo.regionObject=regionList.get(position)
            RegisterStepTwo.region = regionList.get(position).name
            //context.findNavController().navigateUp()
            context.findNavController().navigateUp()

        }
    }
}