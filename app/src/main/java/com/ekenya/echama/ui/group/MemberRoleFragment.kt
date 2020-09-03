package com.ekenya.echama.ui.group


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ekenya.echama.adapter.MemberRoleAdapter
import com.ekenya.echama.databinding.RoleLayoutBinding
import com.ekenya.echama.inc.AppConstants
import com.ekenya.echama.model.Member
import com.ekenya.echama.model.Permission
import com.ekenya.echama.model.User
import com.ekenya.echama.repository.MyApiResponse
import com.ekenya.echama.util.*
import com.ekenya.echama.viewModel.GroupViewModel
import com.ekenya.echama.viewModel.MainViewModel
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class MemberRoleFragment : Fragment() {
    private lateinit var roleAdapter: MemberRoleAdapter

    var startmillis:Long = 0L
    private var _binding: RoleLayoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel
    lateinit var groupViewModel: GroupViewModel
    var action: String = ""
    var currentUser = User("")
    var currentMember = Member("","")
    companion object {
        lateinit var memberPermission: Permission
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        groupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java)

        _binding = RoleLayoutBinding.inflate(inflater, container, false)
        val rootView = binding.root
        //action = "penalty"
       // amount = 10.00
        arguments?.getString("action")?.let {
            action = it
        }
        arguments?.getString("member")?.let {
            currentMember = currentMember.fromJson(it)
        }
        initView()
        initButton()
        initData()

        return rootView
    }
    private fun initMemberRoleRV() {
        binding.rcvRole.layoutManager= GridLayoutManager(context, 3)
        roleAdapter = MemberRoleAdapter(this, memberPermission.roleList)
        binding.rcvRole.adapter = roleAdapter
    }
    private fun initView() {
//        currentMember.name = "Wycliffs"
//        currentMember.phoneNumber = "254715702887"
        binding.txtMemberName.text = currentMember.name
        binding.txtMemberPhone.text = currentMember.phoneNumber
        binding.txtRole.text = currentMember.role
        binding.imgDelete.visibility = View.GONE
        Glide.with(this)
            .asBitmap()
            .load(AppConstants.base_url+"/chama/mobile/req/countries/"+"KE")
            .into( binding.imgUserProfilePhoto)

        binding.imgDelete.setOnClickListener {
            showDeleteDialogue()
        }
    }
    fun showDeleteDialogue(){
        val builder = AlertDialog.Builder(this.requireContext())
        //set title for alert dialog
        builder.setTitle("Remove")
        //set message for alert dialog
        builder.setMessage("Are you sure you want to remove ${currentMember.name} from ${currentMember.groupname} group ")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Yes"){dialogInterface, which ->
            val jsonDetails = HashMap<String,Any>()
            jsonDetails.put("groupid",DataUtil.selectedGrpId)
            jsonDetails.put("memberphonenumber",currentMember.phoneNumber)
            jsonDetails.put("reason","")
            CustomProgressBar.show(this.requireContext())
            groupViewModel.exitGroup(jsonDetails)
            dialogInterface.dismiss()

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

    private fun initData() {
        mainViewModel.userLD.observe(viewLifecycleOwner, Observer { users ->
            if(users.isNotEmpty()){
                currentUser = users[0]
            }
        })
        val jsonGroupDetails = HashMap<String,Any>()
        jsonGroupDetails["groupid"] = DataUtil.selectedGrpId
        jsonGroupDetails["phonenumber"] = currentMember.phoneNumber

        groupViewModel.getMemberPermission(jsonGroupDetails)


        mainViewModel.myApiResponseLD.observe(viewLifecycleOwner, Observer { myApiResponse ->
            CustomProgressBar.hide()
            if(myApiResponse.code == 200){



            }else{
                Timber.v("Error: "+myApiResponse.requestName+" "+myApiResponse.message)
                ToastnDialog.toastError(this.context!!,"Error:"+myApiResponse.message)
            }
        })
        groupViewModel.myGroupApiResponseLD.observe(viewLifecycleOwner, Observer { myApiResponse ->
            CustomProgressBar.hide()
            Timber.v("myApiResponseLD  ${myApiResponse.code} ${myApiResponse.requestName} ${myApiResponse.message}")
            if(myApiResponse.code == 200){
                if(myApiResponse.requestName.contentEquals("getMemberPermissionRequest")) {
                     memberPermission = myApiResponse.responseObj as Permission
                     initMemberRoleRV()
                }

                if(myApiResponse.requestName.contentEquals("updateMemberPermissionRequest")) {
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

    private fun initButton() {
        binding.btnUpdate.setOnClickListener {
            if(validate_info()){
                    val jsonGroupDetails = HashMap<String,Any>()
                    jsonGroupDetails["groupid"] = DataUtil.selectedGrpId
                    jsonGroupDetails["permission"] =  memberPermission.toPermissionJson()
                    jsonGroupDetails["phonenumber"] =  currentMember.phoneNumber
                     Timber.v(jsonGroupDetails.toString())
                     Timber.v(jsonGroupDetails.toJson())
                    groupViewModel.updateMemberPermission(jsonGroupDetails)
                    CustomProgressBar.show(this.requireContext(), "Sending request")
            }
        }
    }

    
    private fun validate_info(): Boolean {

        return true
    }


}
