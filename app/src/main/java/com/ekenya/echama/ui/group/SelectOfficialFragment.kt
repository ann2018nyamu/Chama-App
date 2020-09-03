package com.ekenya.echama.ui.group


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.ekenya.echama.R
import com.ekenya.echama.adapter.SelectOfficialAdapter
import com.ekenya.echama.inc.AppInfo
import com.ekenya.echama.model.AllMembersModel
import com.ekenya.echama.util.DataUtil
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types

/**
 * A simple [Fragment] subclass.
 */
class SelectOfficialFragment : Fragment() {
lateinit var rcvMembers:RecyclerView
    lateinit var memberAdapter:SelectOfficialAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_select_official, container, false)
        rcvMembers=rootView.findViewById(R.id.rcv_select_oficial)
        rcvMembers.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        val contentJson = arguments?.getString("content")
       // val groupMembersJson= Gson().fromJson(contentJson, JsonElement::class.java)

        if (contentJson != null) {
            if (contentJson.isNotEmpty()) {
                populateMembers(contentJson)
            }
        }
        return rootView
    }

    private fun populateMembers(content: String) {
       // if (content.asJsonArray.size() > 0) {
            val bundle = Bundle()
            bundle.putString("content",content.toString())
            bundle.putInt("groupId", DataUtil.selectedGrpId)

            val type = Types.newParameterizedType(MutableList::class.java, AllMembersModel.Member::class.java)
            val adapterRegion: JsonAdapter<List<AllMembersModel.Member>> = AppInfo.moshi.adapter(type)
            var grpList =   adapterRegion.fromJson(content)!!

//            val grpList = ArrayList<AllMembersModel.Member>()
//
//            for (elem in content.asJsonArray) {
//                val job = elem.asJsonObject.get("member")
//                val totalContribution = elem.asJsonObject.get("totalContribution")
//                val grpelement = Gson().fromJson<AllMembersModel.Member>(job,
//                    object : TypeToken<AllMembersModel.Member?>() {}.type)
//                grpelement.totalContribution = totalContribution.asInt
//                Timber.v( grpelement.firstName + "not empty" + totalContribution.asInt)
//                grpList.add(grpelement)
//            }

            memberAdapter = SelectOfficialAdapter(this, grpList,1,"chairman")
            rcvMembers.adapter = memberAdapter
            //btnAttachDoc.setOnClickListener { findNavController().navigate(R.id.nav_upload_document_and_roles,bundle) }
       // }
    }


}
