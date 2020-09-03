package com.ekenya.echama.ui.group


import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.ekenya.echama.MainActivity
import com.ekenya.echama.R
import com.ekenya.echama.adapter.MyPagerAdapter
import com.ekenya.echama.model.Member
import com.ekenya.echama.util.*
import com.ekenya.echama.viewModel.GroupViewModel
import com.google.android.material.tabs.TabLayout
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class GroupDetailsFragment : Fragment() {
    lateinit var viewPAdapter : MyPagerAdapter
    lateinit var tabLayout : TabLayout
    lateinit var viewPager : ViewPager
    var currrentFrag : String = "group"
    lateinit var groupViewModel:GroupViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView =  inflater.inflate(R.layout.fragment_group_details, container, false)
//        var gson = "{\"groupid\":17,\"activemembership\":true,\"description\":\"\",\"groupname\":\"test\",\"groupsize\":1,\"invitesPhonenumbers\":\"null\",\"location\":\"nakuru\"}"
//        arguments?.putString("group",gson)
//        arguments?.getString("group")?.let {
//            Timber.v("group json")
//            Timber.v(it)
//             currrentGroup = Group(0).fromJson(it)
//        }
        groupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java)
        viewPager = rootView.findViewById(R.id.viewPager)
        tabLayout = rootView.findViewById(R.id.tabLayout)
        viewPAdapter= MyPagerAdapter(childFragmentManager)
        //Seting uo function viewpager
        setupViewpager(viewPager)
        tabLayout.setupWithViewPager(viewPager)
        setHasOptionsMenu(true)
        initData()


        return rootView
    }

    private fun initData() {

        groupViewModel.myGroupApiResponseLD.observe(viewLifecycleOwner, Observer { myApiResponse ->
            CustomProgressBar.hide()
            Timber.v("myApiResponseLD  ${myApiResponse.code} ${myApiResponse.requestName} ${myApiResponse.message}")
            if(myApiResponse.code == 200){

                if(myApiResponse.requestName.contentEquals("exitGroupRequest")) {
                    ToastnDialog.toastSuccess(this.requireContext(),myApiResponse.message)
                    findNavController().navigateUp()
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

    private fun setupViewpager(viewPager: ViewPager) {

        viewPAdapter.addFragment(GroupWalletFragment(),"Group wallet")
        viewPAdapter.addFragment(GroupContributionFragment(),"Contribution")
        viewPager.adapter=viewPAdapter


        viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                Timber.v("onPageScrolled $position")
            }

            override fun onPageSelected(position: Int) {
                 Timber.v("onPageSelected $position")
                currrentFrag = "group"
                if(position == 1){
                    currrentFrag = "contribution"
                }
                changeMenu()
            }
            override fun onPageScrollStateChanged(state: Int) {
                Timber.v("onPageScrollStateChanged $state")
            }
        })


    }
    fun changeMenu(){

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_group_menu, menu)
    }
    /**
     * listens to menu item click event
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Timber.v("onOptionsItemSelected group")
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigateUp()
                true
            }
            R.id.menu_gadd_member -> {
                findNavController().navigate(R.id.nav_selectContact)
                true
            }
            R.id.menu_gview_members -> {
                findNavController().navigate(R.id.nav_groupmembers)
                true
            }
            R.id.menu_gpending_approval -> {
                findNavController().navigate(R.id.nav_pending_approval)
                true
            }

            R.id.menu_gdeactivate -> {
                (this.activity as MainActivity).showInDevoplopmentDialogue()
                true
            }
            R.id.menu_view_gaccount -> {
                findNavController().navigate(R.id.nav_addaccount)
                true
            }

            R.id.menu_gfullstatement -> {
                val bundle = Bundle()
                bundle.putString("action","groupstatement")
                findNavController().navigate(R.id.nav_fullstatement,bundle)
                true
            }
            R.id.menu_add_gloan -> {
                findNavController().navigate(R.id.nav_loan_details)
                true
            }
            R.id.menu_add_gdocument -> {

                findNavController().navigate(R.id.nav_upload_document_and_roles)
                true
            }
            R.id.menu_gexit -> {
                showExitGroupDialogue()
                true
            }
            else -> {
                return item.onNavDestinationSelected( findNavController()) || super.onOptionsItemSelected(item)
                //super.onOptionsItemSelected(item)
            }
        }
    }
    fun showExitGroupDialogue(){
        val builder = AlertDialog.Builder(this.requireContext())
        //set title for alert dialog
        builder.setTitle("Exit Group")
        //set message for alert dialog
        builder.setMessage("Are you sure you want to exit group ")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        //performing positive action
        builder.setPositiveButton("Yes"){dialogInterface, which ->
            val jsonDetails = HashMap<String,Any>()
            jsonDetails.put("groupid", DataUtil.selectedGrpId)
            Timber.v(jsonDetails.toJson())
            groupViewModel.exitGroup(jsonDetails)
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
