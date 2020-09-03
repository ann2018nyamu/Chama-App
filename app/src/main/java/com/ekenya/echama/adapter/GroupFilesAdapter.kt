package com.ekenya.echama.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ekenya.echama.R
import com.ekenya.echama.ui.register.RegionCallback
import com.ekenya.echama.ui.register.RegisterStepTwo
import com.ekenya.echama.util.DataUtil
import java.io.File

class GroupFilesAdapter (val context:Fragment):RecyclerView.Adapter<GroupFilesAdapter.RegionViewHolder>(){
     inner class RegionViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
         val txtFileName = itemView.findViewById(R.id.txtFileName) as TextView
         val imgRemove = itemView.findViewById(R.id.imgRemove) as ImageView
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionViewHolder {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return RegionViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_file_item,parent,false))
    }

    override fun getItemCount(): Int {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return DataUtil.grpFilesAr.size
    }

    override fun onBindViewHolder(holder: RegionViewHolder, position: Int) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        holder.txtFileName.text = DataUtil.grpFilesAr[position].name

        holder.imgRemove.setOnClickListener {
            showDeleteDialogue(position)
        }

    }
    fun showDeleteDialogue(position: Int){
        val builder = AlertDialog.Builder(context.requireContext())
        //set title for alert dialog
        builder.setTitle("Delete")
        //set message for alert dialog
        builder.setMessage("Are you sure you want to remove ${DataUtil.grpFilesAr[position].name}")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Yes"){dialogInterface, which ->

            DataUtil.grpFilesAr.removeAt(position)
            notifyItemRangeChanged(position, itemCount -1)
            notifyDataSetChanged()
            dialogInterface.dismiss()

        }
        //performing negative action
        builder.setNegativeButton("No"){dialogInterface, which ->
            dialogInterface.dismiss()
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}