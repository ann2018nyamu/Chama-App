package com.ekenya.echama.ui.group.invites


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.ekenya.echama.R
import com.ekenya.echama.adapter.GroupInvitesAdapter
import com.ekenya.echama.inc.AppInfo
import com.ekenya.echama.model.*
import com.ekenya.echama.ui.register.RegionCallback
import com.ekenya.echama.util.DataUtil
import com.ekenya.echama.util.SharedPrefenceUtil
import com.ekenya.echama.viewModel.GroupViewModel
import com.ekenya.echama.viewModel.ViewModelWrapper
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class ShowGroupInvites : Fragment() {
lateinit var rcvInvites:RecyclerView
    lateinit var groupViewModel:GroupViewModel
    lateinit var adapter: GroupInvitesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView= inflater.inflate(R.layout.fragment_show_group_invites, container, false)
        val listContent = arguments?.getString("content")


        groupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java)
        rcvInvites = rootView.findViewById(R.id.rcvGrpInvites)
        rcvInvites.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)

        processInviteJson(listContent!!)
       /* val jsonInvitesDetails = JSONObject()
        jsonInvitesDetails.put("id", SharedPrefenceUtil().getUserId(this.context!!))
        jsonInvitesDetails.put("page",0)
        jsonInvitesDetails.put("size",10)
        GlobalScope.launch(Dispatchers.Main) {
            getGroupInvites(jsonInvitesDetails)
        }*/
        return rootView
    }

    /**
     * method to process group invite process
     */
    private fun processInviteJson(groupInviteJson: String) {
       // val grpIviteLIst = ArrayList<GroupInvites>()
        val type = Types.newParameterizedType(MutableList::class.java, GroupInvite::class.java)
        val adapterRegion: JsonAdapter<ArrayList<GroupInvite>> = AppInfo.moshi.adapter(type)
         var grpIviteLIst =   adapterRegion.fromJson(groupInviteJson)!!

//        for(element in groupInviteJson.asJsonArray){
//            val job = element.asJsonObject.get("memberGroups")
//            val grpelement = Gson().fromJson<GroupInvitesModel>(job,object : TypeToken<GroupInvitesModel?>() {}.type)
//            val inviteeJson = grpelement.creator
//            val inviteeModel = Gson().fromJson<AllMembersModel.Member>(inviteeJson,object : TypeToken<AllMembersModel.Member?>() {}.type)
//            val inviteeName = inviteeModel.firstName
//            Timber.v("element %s",grpelement.name+"not empty")
//            grpIviteLIst.add(GroupInvites(grpelement.id,grpelement.name,inviteeName))
//        }
        adapter = GroupInvitesAdapter(this,grpIviteLIst)
        rcvInvites.adapter = adapter
    }

     fun getGroupInvites(json:HashMap<String,Any>) {
        groupViewModel.getGrpInvites()
//            .observe(this, Observer {
//            when(it){
//                is ViewModelWrapper.error -> Log.e("invitesError","error occurred"+it.error)
//                is ViewModelWrapper.response -> handleGrpInvitesResponse(it.value)
//            }
//        })
    }

    private fun handleGrpInvitesResponse(value: String) {
//        val gson = GsonBuilder().create()
//        Timber.v("regions %s",value)
//        val groupdatajson= gson.fromJson(value, JsonElement::class.java)
//        val content = groupdatajson.asJsonObject.get("content")//.asJsonArray.get(0).asJsonObject.get("memberGroups")//.asJsonArray.get(0).asJsonObject.get("name")
//        //val regionList= gson.fromJson(grplist,Array<AllGroupsModel.MemberGroups>::class.java).toList()
//        Timber.v("data %s",content.toString())
//        if(content.asJsonArray.size()<1){
//            Timber.v("emptyList %s","empty llist")
//        }


    }


}
