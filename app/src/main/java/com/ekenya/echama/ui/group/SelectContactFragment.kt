package com.ekenya.echama.ui.group


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.ekenya.echama.R
import com.ekenya.echama.adapter.SelectContactAdapter
import com.ekenya.echama.inc.AppInfo
import com.ekenya.echama.model.Group
import com.ekenya.echama.model.Member
import com.ekenya.echama.util.*
import com.ekenya.echama.viewModel.GroupViewModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class SelectContactFragment : Fragment() {
lateinit var rcvContacts:RecyclerView
    lateinit var contactAdapter:SelectContactAdapter
    lateinit var listmodel:ArrayList<Member>
    lateinit var btnSubmit:Button
    val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 10
    lateinit var groupViewModel: GroupViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        groupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java)
        val rootView= inflater.inflate(R.layout.fragment_select_contact, container, false)
        rcvContacts=rootView.findViewById(R.id.rcv_contacts)
        rcvContacts.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        btnSubmit = rootView.findViewById(R.id.btnSubmit)
        btnSubmit.setOnClickListener {
            if(DataUtil.selectedGrpId != 0) {
                addGroupMembers()
            } else {
                findNavController().navigateUp()
            }
        }
        checknGrantPermmision()
        initData()
       //getContactList()
        return rootView
    }

    private fun initData() {
        groupViewModel.myGroupApiResponseLD.observe(viewLifecycleOwner, Observer { myApiResponse ->
            CustomProgressBar.hide()
            Timber.v("myApiResponseLD  ${myApiResponse.code} ${myApiResponse.requestName} ${myApiResponse.message}")

            if(myApiResponse.code == 200){

                if(myApiResponse.requestName.contentEquals("addGroupMembersRequest")) {
                    Timber.v("size ");
                    successAlert(message = myApiResponse.message)
                    ToastnDialog.toastSuccess(this.context!!,  myApiResponse.message)
                   // findNavController().navigateUp()
                }
            } else {
                if(myApiResponse.message.isNotEmpty()) {
                    ToastnDialog.toastError(this.context!!, "Error:" + myApiResponse.message)
                }
            }

        })
    }

    fun addGroupMembers(){
        var jsonGroupDetails = HashMap<String,Any>()
        var phoneAr = ArrayList<HashMap<String,String>>()
        for(member in DataUtil.selectedContacts){
            //  phoneAr.add(member.phoneNumber)
            var dt = HashMap<String,String>()
            dt["phonenumber"] = member.getFPhoneNumber() // member.phoneNumber
            dt["role"] = member.role.toString()
            phoneAr.add(dt)
        }
        jsonGroupDetails["groupid"] = DataUtil.selectedGrpId
        jsonGroupDetails["phonenumbersandrole"] = phoneAr
        val type = Types.newParameterizedType(List::class.java, Any::class.java)
        val adapterRegion: JsonAdapter<List<Any>> = AppInfo.moshi.adapter(type)
        Timber.v(phoneAr.toList().toJsonObj())
        Timber.v(jsonGroupDetails.toJson())
        CustomProgressBar.show(this.requireContext(),"Sending request")
        groupViewModel.addGroupMembers(jsonGroupDetails)
    }
    fun checknGrantPermmision(){
        val permission = ContextCompat.checkSelfPermission(activity!!.applicationContext,
            Manifest.permission.READ_CONTACTS)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Timber.v("Permission to record denied")
            makePermisionRequest()
        }else{
            GlobalScope.launch(Dispatchers.Main) {
                getContactList()
            }
        }
    }

    private fun makePermisionRequest() {
        requestPermissions( arrayOf(Manifest.permission.READ_CONTACTS), MY_PERMISSIONS_REQUEST_READ_CONTACTS)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_CONTACTS -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    GlobalScope.launch(Dispatchers.Main) {
                        getContactList()
                    }
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    findNavController().navigateUp()
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }
    fun getContactList(){
        context?.let { ContactHelper(it).getAllContacts(this,object : ContactHelper.ContactLoadComplete {
            override fun getAllContact(mContactArrayList: ArrayList<ContactHelper.PhoneList>, mNameArrayList: ArrayList<String>) {
                //code here
                 listmodel = ArrayList<Member>()
                if(mContactArrayList.size > 0) {
                    Timber.v("listcontacts %s",mContactArrayList.get(0).phoneNumber+mContactArrayList.size)
                    for (x in mContactArrayList) {
                        Timber.v("names  %s", x.name + x.phoneNumber)
                        listmodel.add(
                            Member(
                                x.name,
                                x.phoneNumber
                            )
                        )
                        contactAdapter = SelectContactAdapter(this@SelectContactFragment, listmodel)
                    }
                }
                rcvContacts.adapter = contactAdapter
            }

        })

        }

    }


}
