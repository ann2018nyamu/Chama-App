package com.ekenya.echama.ui.group


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.ekenya.echama.adapter.GroupTrxAdapter
import com.ekenya.echama.adapter.TrxAdapter
import com.ekenya.echama.databinding.FragmentGroupWalletBinding
import com.ekenya.echama.model.AllMembersModel
import com.ekenya.echama.model.Group
import com.ekenya.echama.model.Transaction
import com.ekenya.echama.util.*
import com.ekenya.echama.viewModel.GroupViewModel
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class GroupWalletFragment : Fragment() {
//lateinit var rcvMembers:RecyclerView
lateinit var groupViewModel:GroupViewModel
 // lateinit var btnAttachDoc:Button
    val grpList = ArrayList<AllMembersModel.Member>()
    lateinit var bundle:Bundle
    var activityList:ArrayList<Transaction> = ArrayList()
    lateinit var recentAdapter:TrxAdapter
    private var _binding: FragmentGroupWalletBinding? = null
    private val binding get() = _binding!!
    var currrentGroup : Group = Group(0)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGroupWalletBinding.inflate(inflater, container, false)
        val rootView = binding.root

       // val rootView = inflater.inflate(R.layout.fragment_group_wallet, container, false)
        groupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java)
       // rcvMembers = rootView.findViewById(R.id.rcvGrpMembers)
       // btnAttachDoc = rootView.findViewById(R.id.btnAddRoles)
        
        Timber.v("selectedGrp %s",DataUtil.selectedGrpId.toString())
        initTextView()
        initData()
        initGroupRecentActivitiesRV()
        return rootView
    }
    private fun initGroupRecentActivitiesRV() {
        binding.rcvGrpMembersContribution.layoutManager= LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        recentAdapter = TrxAdapter(this, activityList)
        binding.rcvGrpMembersContribution.adapter = recentAdapter
    }

    private fun initData() {

        val jsonGroupDetails = HashMap<String,Any>()
        jsonGroupDetails["groupid"] = DataUtil.selectedGrpId
        jsonGroupDetails["page"] = 0
        jsonGroupDetails["size"] = 10

       // groupViewModel.getAllMembers(jsonGroupDetails)
        groupViewModel.getGroupDetails(DataUtil.selectedGrpId)?.observe(viewLifecycleOwner, Observer {
            Timber.v("it.groupname")
            if(it != null){
                Timber.v(it.groupname)
                currrentGroup = it
                initTextView()
            }

        })

        groupViewModel.getGroupTransactions(jsonGroupDetails).observe(viewLifecycleOwner, Observer {
            Timber.v("getGroupTransactions size ${it.size}")
            if(it.isNotEmpty()){
                activityList = it as ArrayList<Transaction>
                initGroupRecentActivitiesRV()
            }else{
                binding.txtEmptyDesc.visibility = View.VISIBLE
            }
        })

        groupViewModel.myGroupApiResponseLD.observe(viewLifecycleOwner, Observer { myApiResponse ->
            CustomProgressBar.hide()
            Timber.v("myApiResponseLD  ${myApiResponse.code} ${myApiResponse.requestName} ${myApiResponse.message}")
            if(myApiResponse.code == 200){
                if(myApiResponse.requestName.contentEquals("getGrpTrxRequest")) {
                   val gtrx = myApiResponse.responseObj as List<Transaction>
                    Timber.v("grpTrxList ${gtrx.size}")
                   if( gtrx.isEmpty()){
                       binding.txtEmptyDesc.visibility = View.VISIBLE
                       ToastnDialog.toastSuccess(this.requireContext(),myApiResponse.message)
                   }else{
                       binding.txtEmptyDesc.visibility = View.GONE
                   }
                }

//                if(myApiResponse.requestName.contentEquals("getGrpTrxRequest")){
//
//                }

            }else  if(myApiResponse.code == 700) {
                CustomProgressBar.show(this.requireContext())
            }else{
                if(myApiResponse.message.isNotEmpty()){
                    ToastnDialog.toastError(this.context!!,"Error:"+myApiResponse.message)
                    if(myApiResponse.message.toLowerCase().contains("token expired")){
                        expiredTokenDialogue()
                    }
                }
            }
        })
    }

    private fun initTextView() {
        binding.tvGroupDescription.text = currrentGroup.description
        binding.txtGroupWalletBalance.text = "Wallet Balance: "+currrentGroup.getFWalletBalance()
        binding.txtGroupBalance.text = "Group Balance: "+ currrentGroup.getFTotalContributions()
    }


}
