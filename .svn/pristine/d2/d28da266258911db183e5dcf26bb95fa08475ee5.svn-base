package com.ekenya.echama.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.ekenya.echama.R
import com.ekenya.echama.model.Role
import com.ekenya.echama.ui.group.MemberRoleFragment
import com.ekenya.echama.viewModel.GroupViewModel
import kotlinx.android.synthetic.main.item_role.view.*

class MemberRoleAdapter (val frag: Fragment, val roleList:ArrayList<Role>):RecyclerView.Adapter<MemberRoleAdapter.MembersAdapterViewHolder>(){
    val groupModel = ViewModelProvider(frag).get(GroupViewModel::class.java)

    inner class MembersAdapterViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var cbView = itemView.cbView
        var cbEdit = itemView.cbEdit
        var cbDelete = itemView.cbDelete
        var cbCreate = itemView.cbCreate
        var txtRuleName = itemView.txtRuleName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MembersAdapterViewHolder {
        return MembersAdapterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_role,parent,false))
    }

    override fun getItemCount(): Int {
      return MemberRoleFragment.memberPermission.roleList.size
    }

    override fun onBindViewHolder(holder: MembersAdapterViewHolder, pos: Int) {
        holder.txtRuleName.text = MemberRoleFragment.memberPermission.roleList[pos].getFRoleName()
        holder.cbCreate.isChecked = MemberRoleFragment.memberPermission.roleList[pos].cancreate
        holder.cbView.isChecked = MemberRoleFragment.memberPermission.roleList[pos].canview
        holder.cbDelete.isChecked = MemberRoleFragment.memberPermission.roleList[pos].candelete
        holder.cbEdit.isChecked = MemberRoleFragment.memberPermission.roleList[pos].canedit

        holder.cbCreate.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            MemberRoleFragment.memberPermission.roleList[pos].cancreate = isChecked

        }
        holder.cbView.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            MemberRoleFragment.memberPermission.roleList[pos].cancreate = isChecked
        }

        holder.cbDelete.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            MemberRoleFragment.memberPermission.roleList[pos].cancreate = isChecked
        }
        holder.cbEdit.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            MemberRoleFragment.memberPermission.roleList[pos].canedit = isChecked
        }



    }

}