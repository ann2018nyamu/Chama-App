package com.ekenya.echama.ui.group


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.ekenya.echama.R
import com.ekenya.echama.adapter.GroupContributionAdapter
import com.ekenya.echama.databinding.FragmentGroupContributionBinding
import com.ekenya.echama.databinding.FragmentGroupWalletBinding
import com.ekenya.echama.inc.AppInfo
import com.ekenya.echama.model.AllContributionModel
import com.ekenya.echama.model.AllMembersModel
import com.ekenya.echama.model.Contribution
import com.ekenya.echama.util.*
import com.ekenya.echama.viewModel.GroupViewModel
import com.ekenya.echama.viewModel.ViewModelWrapper
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.w3c.dom.Text
import timber.log.Timber
import java.security.acl.Group

/**
 * A simple [Fragment] subclass.
 */
class GroupContributionFragment : Fragment() {
    private lateinit var contributionList: ArrayList<Contribution>
   // lateinit var rcvContribution : RecyclerView
    lateinit var groupViewModel : GroupViewModel
    lateinit var contributionAdapter:GroupContributionAdapter
    private var _binding: FragmentGroupContributionBinding? = null

    private val binding get() = _binding!!
    val cPage = 0
    val cSize = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGroupContributionBinding.inflate(inflater, container, false)
        val rootView = binding.root
        //val rootView= inflater.inflate(R.layout.fragment_group_contribution, container, false)
        groupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java)
      //  val btnCreateContribution = rootView.findViewById(R.id.btnCreate) as Button
      //  btnCreateContribution.setOnClickListener { findNavController().navigate(R.id.nav_create_contribution) }

        initData()
        initButton()
        return rootView
    }

    private fun initButton() {
        binding.btnCreate.setOnClickListener { findNavController().navigate(R.id.nav_create_contribution) }
    }

    private fun initGroupContributionRV() {
        binding.rcvGrpContribution.layoutManager= LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        contributionAdapter = GroupContributionAdapter(this,contributionList)
        binding.rcvGrpContribution.adapter = contributionAdapter
    }

    private fun initData() {

        val jsonContribution = HashMap<String,Any>()
        jsonContribution.put("groupid", DataUtil.selectedGrpId)
        jsonContribution.put("page",cPage)
        jsonContribution.put("size",cSize)

        groupViewModel.getGrpContributions(jsonContribution).observe(viewLifecycleOwner, Observer {
            Timber.v("getGroupContribution size ${it.size}")
            if(it.isNotEmpty()){
                contributionList = it as ArrayList<Contribution>
                initGroupContributionRV()
                binding.txtEmptyDesc.visibility = View.GONE
            }else{
                binding.txtEmptyDesc.visibility = View.VISIBLE
            }
        })
        groupViewModel.myGroupApiResponseLD.observe(viewLifecycleOwner, Observer { myApiResponse ->
            CustomProgressBar.hide()
            Timber.v("myApiResponseLD  ${myApiResponse.code} ${myApiResponse.requestName} ${myApiResponse.message}")
            if(myApiResponse.code == 200){
                if(myApiResponse.requestName.contentEquals("getGrpContributionRequest")) {
                    val gtrx = myApiResponse.responseObj as List<Contribution>
                    Timber.v("grpTrxList ${gtrx.size}")
                    if( gtrx.isEmpty()){
                        binding.txtEmptyDesc.visibility = View.VISIBLE
                       /// ToastnDialog.toastSuccess(this.requireContext(),myApiResponse.message)
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






}
