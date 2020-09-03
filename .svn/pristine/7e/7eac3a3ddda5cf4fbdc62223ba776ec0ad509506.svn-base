package com.ekenya.echama.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.ekenya.echama.R
import com.ekenya.echama.model.Member
import com.ekenya.echama.util.DataUtil
import kotlinx.android.synthetic.main.layout_addedcontact.view.*

class AddedContactsAdapter(val context:Fragment,val contactList: ArrayList<Member>):RecyclerView.Adapter<AddedContactsAdapter.AddedContactViewHolder>() {

    inner class AddedContactViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var name = itemView.findViewById(R.id.txt_member_name) as TextView
        var phone = itemView.txt_meember_phone
        var imgclose = itemView.img_check
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddedContactViewHolder {
        return AddedContactViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_addedcontact,parent,false))
    }

    override fun getItemCount(): Int {
       return contactList.size
    }

    override fun onBindViewHolder(holder: AddedContactViewHolder, position: Int) {
        holder.name.setText(contactList[position].name)
        holder.phone.setText(contactList[position].phoneNumber)
        holder.imgclose.setOnClickListener {
            showDeleteDialogue(position)

        }
    }
    fun showDeleteDialogue(position: Int){
        val builder = AlertDialog.Builder(context.requireContext())
        //set title for alert dialog
        builder.setTitle("Delete")
        //set message for alert dialog
        builder.setMessage("Are you sure you want to remove ${contactList[position].name}")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Yes"){dialogInterface, which ->
            contactList.remove(contactList[position])
            notifyItemRangeChanged(position, getItemCount()-1)
            notifyDataSetChanged()
            DataUtil.selectedContacts = contactList
            dialogInterface.dismiss()
        }
        //performing cancel action
//        builder.setNeutralButton("Cancel"){dialogInterface , which ->
//            Toast.makeText(applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_LONG).show()
//        }
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