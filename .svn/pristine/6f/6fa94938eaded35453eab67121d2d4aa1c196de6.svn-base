package com.ekenya.echama.ui.group


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.ekenya.echama.MainActivity
import com.ekenya.echama.R
import com.ekenya.echama.adapter.MembersAdapter
import com.ekenya.echama.adapter.MyPagerAdapter
import com.ekenya.echama.adapter.TrxAdapter
import com.ekenya.echama.databinding.GroupMembersBinding
import com.ekenya.echama.model.Member
import com.ekenya.echama.util.*
import com.ekenya.echama.viewModel.GroupViewModel
import com.google.android.material.tabs.TabLayout
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class GroupMembersFragment : Fragment() {

    private  var memberList: ArrayList<Member> = ArrayList()
    private lateinit var memberAdapter: MembersAdapter
    private var _binding: GroupMembersBinding? = null
    private val binding get() = _binding!!
    lateinit var groupViewModel:GroupViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = GroupMembersBinding.inflate(inflater, container, false)
        val rootView = binding.root
        groupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java)

        initData()

        return rootView
    }

    private fun initData() {
        val jsonGroupDetails = HashMap<String,Any>()
        jsonGroupDetails["groupid"] = DataUtil.selectedGrpId
        jsonGroupDetails["page"] = 0
        jsonGroupDetails["size"] = 100
        groupViewModel.getGroupMembers(jsonGroupDetails)?.observe(viewLifecycleOwner, Observer { members ->
            Timber.v("getGroupMembers ${members.size}")
            memberList = members as ArrayList<Member>
            initGroupRecentActivitiesRV()
        })



        groupViewModel.myGroupApiResponseLD.observe(viewLifecycleOwner, Observer { myApiResponse ->
            CustomProgressBar.hide()
            Timber.v("myApiResponseLD  ${myApiResponse.code} ${myApiResponse.requestName} ${myApiResponse.message}")
            if(myApiResponse.code == 200){
                if(myApiResponse.requestName.contentEquals("getGroupMembersRequest")) {
                    val gtrx = myApiResponse.responseObj as List<Member>
                    Timber.v("grpTrxList ${gtrx.size}")
                }

                if(myApiResponse.requestName.contentEquals("exitGroupRequest")) {
                       ToastnDialog.toastSuccess(this.requireContext(),myApiResponse.message)
                }

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

    private fun initGroupRecentActivitiesRV() {
        binding.rcvGrpMembers.layoutManager= LinearLayoutManager(context,
            RecyclerView.VERTICAL,false)
        memberAdapter = MembersAdapter(this, memberList)
        binding.rcvGrpMembers.adapter = memberAdapter
    }



    /**
     * listens to menu item click event
     */

}
