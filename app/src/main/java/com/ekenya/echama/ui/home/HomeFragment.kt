package com.ekenya.echama.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.ekenya.echama.R
import com.ekenya.echama.adapter.MyPagerAdapter
import com.ekenya.echama.viewModel.GroupViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import org.json.JSONObject
import timber.log.Timber

class HomeFragment : Fragment() {

    lateinit var viewPAdapter : MyPagerAdapter
    lateinit var tabLayout : TabLayout
    lateinit var viewPager : ViewPager
    lateinit var fabInvites : FloatingActionButton
    lateinit var groupViewModel: GroupViewModel
    lateinit var tvInvitationNumber : TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        viewPager = root.findViewById(R.id.viewPager)
        tabLayout = root.findViewById(R.id.tabLayout)
        tvInvitationNumber = root.findViewById(R.id.tvInvitationNumber)
        groupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java)

        fabInvites =root.findViewById(R.id.fab)

        viewPAdapter=MyPagerAdapter(childFragmentManager)
        //Seting uo function viewpager
        SetupViewpager(viewPager)
        tabLayout.setupWithViewPager(viewPager)
        val jsonInvitesDetails = HashMap<String,Any>()
       // SharedPrefenceUtil().getUserId(this.context!!)?.let { jsonInvitesDetails.put("id", it) }//
        jsonInvitesDetails.put("page",0)
        jsonInvitesDetails.put("size",10)
//        GlobalScope.launch(Dispatchers.Main) {
//            getGroupInvites(jsonInvitesDetails)
//        }

        //Todo Check on group invites
        Timber.v(" getGrpInvites")
       // groupViewModel.getGrpInvites(jsonInvitesDetails)

        initData()
        return root
    }

    private fun initData() {
        //Todo remove code meant to bypass login
    }

    private fun SetupViewpager(viewPager: ViewPager) {
        viewPAdapter.addFragment(MyGroups(),"My Groups")
        viewPAdapter.addFragment(RecentActivitiesFragment(),"Recent Activity")

        viewPager.adapter=viewPAdapter
    }
     fun getGroupInvites(json:JSONObject) {

//            .observe(viewLifecycleOwner, Observer {
//            when(it){
//                is ViewModelWrapper.error -> Log.e("invitesError","error occurred"+it.error)
//                is ViewModelWrapper.response -> handleGrpInvitesResponse(it.value)
//            }
//        })
    }

    private fun handleGrpInvitesResponse(value: String) {
      //  val gson = GsonBuilder().create()
        Timber.v(value)
        if (value == "") {
            //val groupdatajson = gson.fromJson(value, JsonElement::class.java)
          //  val content =  groupdatajson.asJsonObject.get("content")//.asJsonArray.get(0).asJsonObject.get("memberGroups")//.asJsonArray.get(0).asJsonObject.get("name")
            //val regionList= gson.fromJson(grplist,Array<AllGroupsModel.MemberGroups>::class.java).toList()
//            Timber.v( content.toString())
//            if (content.asJsonArray.size() > 0) {
//                tvInvitationNumber.setText(content.asJsonArray.size().toString())
//                val bundle = Bundle()
//                bundle.putString("content", content.toString())
//                Timber.v( "empty llist")
//                fabInvites.setOnClickListener {
//                    findNavController().navigate(
//                        R.id.nav_show_invites,
//                        bundle
//                    )
//                }
//            } else {
//                Toast.makeText(activity, "No Invites", Toast.LENGTH_SHORT)
//            }

        }


    }


}