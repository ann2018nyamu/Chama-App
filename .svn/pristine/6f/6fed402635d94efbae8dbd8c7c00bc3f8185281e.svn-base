package com.ekenya.echama.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.ekenya.echama.R
import com.ekenya.echama.model.AllMembersModel
import com.ekenya.echama.model.Member
import com.ekenya.echama.repository.MyApiResponse
import com.ekenya.echama.util.DataUtil
import com.ekenya.echama.viewModel.GroupViewModel
import kotlinx.android.synthetic.main.group_member_item.view.*
import kotlinx.android.synthetic.main.item_gmember_contribution.view.*
import timber.log.Timber

class MembersAdapter (val frag: Fragment, val memberList:ArrayList<Member>):RecyclerView.Adapter<MembersAdapter.MembersAdapterViewHolder>(){
    val groupModel = ViewModelProvider(frag).get(GroupViewModel::class.java)

    inner class MembersAdapterViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var name = itemView.findViewById(R.id.txt_member_name) as TextView
        var phone = itemView.txt_meember_phone
        var imgDelete = itemView.imgDelete
        var btnAddRole = itemView.btnAddRole
        var txtRole = itemView.txtRole
        var rootVCL = itemView.rootVCL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MembersAdapterViewHolder {
        return MembersAdapterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.group_member_item,parent,false))
    }

    override fun getItemCount(): Int {
      return memberList.size
    }

    override fun onBindViewHolder(holder: MembersAdapterViewHolder, position: Int) {
       // val lastname? = memberList.get(position)?.lastName? :null
        holder.phone.setText(memberList[position].phoneNumber)
        holder.name.setText(memberList[position].name)
        var roleAr = arrayListOf("Chairperson","Treasurer","Secretary","Member")


        holder.btnAddRole.setOnClickListener {
            showRoleAlert(holder,position)
        }
        holder.txtRole.text = "Member"
        holder.imgDelete.setOnClickListener {
            showDeleteDialogue(holder,position)
        }
        holder.rootVCL.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("member",memberList[position].toJson())
            NavHostFragment.findNavController(frag).navigate(R.id.nav_memberrole,bundle)
        }

        if(!memberList[position].activemembership){
            holder.imgDelete.visibility = View.GONE
            holder.btnAddRole.visibility = View.GONE
        }

    }
    fun showDeleteDialogue( holder: MembersAdapterViewHolder,
                            position: Int){
        val builder = AlertDialog.Builder(frag.requireContext())
        //set title for alert dialog
        builder.setTitle("Remove")
        //set message for alert dialog
        builder.setMessage("Are you sure you want to remove ${memberList[position].name} from ${memberList[position].groupname} group ")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Yes"){dialogInterface, which ->
            val jsonDetails = HashMap<String,Any>()
            jsonDetails.put("groupid",DataUtil.selectedGrpId)
            jsonDetails.put("memberphonenumber",memberList[position].phoneNumber)
            jsonDetails.put("reason","")
            groupModel.exitGroup(jsonDetails)
            dialogInterface.dismiss()
            memberList.remove(memberList[position])
            notifyItemRangeChanged(position, itemCount -1)
            notifyDataSetChanged()
            groupModel.myGroupApiResponseLD.postValue(MyApiResponse(700,"removeMember","",""))

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
    private fun showRoleAlert(
        holder: MembersAdapterViewHolder,
        position: Int
    ) {
        var items = arrayOf("Chairperson","Treasurer","Secretary","Member")
        // val items = arrayOf("Red", "Orange", "Yellow", "Blue")
        val builder = AlertDialog.Builder(frag.requireContext())
//        val builder = AlertDialog.Builder(ContextThemeWrapper(frag.requireContext(),R.style.AlertDialogCustom))
        with(builder)
        {
            setTitle("Choose a group role for ${memberList[position].name}")
            var checkedIt:Int = 3
            //getting default key
            var i = 0
            for(item in items){
                if(item.toLowerCase().contentEquals(memberList[position].role.toString().toLowerCase())){
                    checkedIt = i
                } else {
                    i++
                }
            }

            setSingleChoiceItems(items, checkedIt
            ) { dialog, which ->
                memberList[position].role = items[which]
                holder.txtRole.text = items[which]
                Timber.v(items[which])
            }

            builder.setPositiveButton("Okay"){dialogInterface, which ->
                dialogInterface.dismiss()
            }

            show()
        }
    }
}