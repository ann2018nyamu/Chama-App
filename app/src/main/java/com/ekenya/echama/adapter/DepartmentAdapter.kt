package com.ekenya.echama.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ekenya.echama.R
import com.ekenya.echama.ui.register.RegionCallback
import com.ekenya.echama.ui.register.RegisterStepTwo

class DepartmentAdapter(val context: Fragment,val deptList:List<RegionCallback.Department> ):RecyclerView.Adapter<DepartmentAdapter.DeparmentVewHolder>(){

    inner class DeparmentVewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
       // val tvDepartmentName =itemView.findViewById(R.id.tvregionName) as TextView
        val tvDepartmentName =itemView.findViewById(R.id.tvCountryName) as TextView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeparmentVewHolder {
        return DeparmentVewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_region,parent,false))
    }

    override fun getItemCount(): Int {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return deptList.size
    }

    override fun onBindViewHolder(holder: DeparmentVewHolder, position: Int) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        holder.tvDepartmentName.setText(deptList.get(position).name)
        holder.itemView.setOnClickListener {
            RegisterStepTwo.departmentName = deptList.get(position).name
            context.findNavController().navigateUp()
        }
    }
}