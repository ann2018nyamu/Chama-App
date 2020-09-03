package com.ekenya.echama.ui.group


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.ekenya.echama.R
import com.ekenya.echama.adapter.AddedContactsAdapter
import com.ekenya.echama.inc.AppInfo
import com.ekenya.echama.model.Member
import com.ekenya.echama.model.Country
import com.ekenya.echama.model.Group
import com.ekenya.echama.model.ScheduleType
import com.ekenya.echama.util.*
import com.ekenya.echama.viewModel.GroupViewModel
import com.ekenya.echama.viewModel.MainViewModel
import com.google.android.material.textfield.TextInputLayout
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types

import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class CreateGroupFragment : Fragment() {
    lateinit var rootConstraintLay : ConstraintLayout
    lateinit var etTown : EditText
    lateinit var mainViewModel:MainViewModel
    lateinit var groupViewModel: GroupViewModel
    lateinit var countyList:ArrayList<String>
    lateinit var modelGroupList:List<ScheduleType>
    lateinit var groupTypeList : ArrayList<String>
    lateinit var createGroupToolbar: Toolbar

    lateinit var groupTypeMmodelList:List<ScheduleType>
    lateinit var etGroupName:EditText
    lateinit var tlGroupName:TextInputLayout
    lateinit var tlGroupDesc:TextInputLayout
    lateinit var tlTown:TextInputLayout
    lateinit var etxtGroupDesc:EditText
    lateinit var btnCreateGroup:Button
    lateinit var tvAddMember:TextView
    lateinit var txtUploadDoc:TextView
    lateinit var rcvContacts:RecyclerView
    val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 10
    lateinit var rootView:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         rootView= inflater.inflate(R.layout.fragment_create_group, container, false)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        groupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java)
        countyList = ArrayList()
        groupTypeList = ArrayList()
        populateCountry()
        checknGrantPermmision()
        etTown = rootView.findViewById(R.id.etTown)
        etxtGroupDesc = rootView.findViewById(R.id.etxtGroupDesc)
        etGroupName = rootView.findViewById(R.id.etGroupName)
        tlGroupName = rootView.findViewById(R.id.tlGroupName)
        tlGroupDesc = rootView.findViewById(R.id.tlGroupDesc)
        tlTown = rootView.findViewById(R.id.tlTown)
        rcvContacts = rootView.findViewById(R.id.rcvAddedContacts)
        btnCreateGroup = rootView.findViewById(R.id.btnCreateGroup)

        btnCreateGroup.setOnClickListener { handleCreateGroupClick()}

        initImageV()
        initRV()
        initData()
        return rootView
    }

    private fun initData() {
        groupViewModel.myGroupApiResponseLD.observe(viewLifecycleOwner, Observer { myApiResponse ->
            CustomProgressBar.hide()
            Timber.v("myApiResponseLD  ${myApiResponse.code} ${myApiResponse.requestName} ${myApiResponse.message}")

            if(myApiResponse.code == 200){
                if (myApiResponse.requestName.contentEquals("createGroupRequest")){

                   val group = myApiResponse.responseObj as Group

                    if(DataUtil.grpFilesAr.isNotEmpty()) {
                        ToastnDialog.toastSuccess(this.context!!,   myApiResponse.message)
                        CustomProgressBar.show(this.requireContext(),"Uploading image please wait")
                        groupViewModel.uploadGrpFiles(group.groupid, DataUtil.getFileMultipart())
                    }else{
                        successAlert(message = myApiResponse.message)
                    }
                }
                if(myApiResponse.requestName.contentEquals("uploadGrpFileRequest")) {
                    DataUtil.grpFilesAr.removeAll(DataUtil.grpFilesAr)
                    Timber.v("size ${DataUtil.grpFilesAr}");
                    successAlert(message = myApiResponse.message)
                }
            } else {
                if(myApiResponse.message.isNotEmpty()) {
                    ToastnDialog.toastError(this.context!!, "Error:" + myApiResponse.message)
                }
            }

        })
    }

    private fun clearData() {
        etGroupName.text.clear()
        etxtGroupDesc.text.clear()
        etTown.text.clear()
        DataUtil.selectedContacts = ArrayList()
        DataUtil.grpFilesAr.removeAll(DataUtil.grpFilesAr)
    }

    private fun initRV() {
        rcvContacts.layoutManager = GridLayoutManager(context,3,RecyclerView.VERTICAL,false)
        val adapter = AddedContactsAdapter(this,
            DataUtil.selectedContacts as ArrayList<Member>
        )
        rcvContacts.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        tvAddMember.text = "Add Members(${DataUtil.selectedContacts.size})"
        txtUploadDoc.text = "Group Document(${DataUtil.grpFilesAr.size})"
    }

    private fun initImageV() {
         val imgAddMember = rootView.findViewById(R.id.imgAddMember) as ImageView
         tvAddMember = rootView.findViewById(R.id.textView8) as TextView
         val imguploadDoc = rootView.findViewById(R.id.imguploadDoc) as ImageView
         txtUploadDoc = rootView.findViewById(R.id.txtUploadDoc) as TextView

        imgAddMember.setOnClickListener { findNavController().navigate(R.id.nav_selectContact) }
        tvAddMember.setOnClickListener { findNavController().navigate(R.id.nav_selectContact) }

        imguploadDoc.setOnClickListener { findNavController().navigate(R.id.nav_upload_document_and_roles) }
        txtUploadDoc.setOnClickListener { findNavController().navigate(R.id.nav_upload_document_and_roles) }

        //Todo  provide functionality to add group photo/image
        val imgAddGroupImage = rootView.findViewById(R.id.imgAddGroupImage) as ImageView

    }



    fun getContacts() {
       //val list= ContactList.getContacts(activity)
        //Log.e("list",list.get(0).name+""+list.size)
      /*  if(checkpermision()){
            getContactList()
        }*/
       // if(checkpermision()) {
            context?.let {
                ContactHelper(it).getAllContacts(this, object : ContactHelper.ContactLoadComplete {
                    override fun getAllContact(
                        mContactArrayList: ArrayList<ContactHelper.PhoneList>,
                        mNameArrayList: ArrayList<String>
                    ) {
                        //code here
                        Timber.v(
                            "list %s",
                            mContactArrayList.get(0).phoneNumber + mContactArrayList.get(0).name + mContactArrayList.size
                        )
                    }
                })
           // }
        }


    }

    fun getContactList(){
        context?.let { ContactHelper(it).getAllContacts(this,object : ContactHelper.ContactLoadComplete {
            override fun getAllContact(mContactArrayList: ArrayList<ContactHelper.PhoneList>, mNameArrayList: ArrayList<String>) {
                //code here
                Timber.v("list %s",mContactArrayList.get(0).phoneNumber+mContactArrayList.size)

            }
        })}
    }
    fun checknGrantPermmision(){
        val permission = ContextCompat.checkSelfPermission(activity!!.applicationContext,
            Manifest.permission.READ_CONTACTS)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i("permsions", "Permission to record denied")
            makePermisionRequest()
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
                    getContacts()
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
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

    private fun handleCreateGroupClick() {
        //Check verification
        val entireJsonHolder = JSONObject()
        val jsonGroupDetails = HashMap<String,Any>()
        var invitePhoneNumbers = JSONArray()
//        etGroupName.setText("test")
//        etxtGroupDesc.setText("description")
//        etTown.setText("Nakuru")

        if(validInfo()){
            jsonGroupDetails["groupname"] = etGroupName.text.toString().trim()
            jsonGroupDetails["description"] = etxtGroupDesc.text.toString().trim()
            jsonGroupDetails["location"] = etTown.text.toString()
           // var phoneAr = ArrayList<String>()
            var phoneAr = ArrayList<HashMap<String,String>>()
            for(member in DataUtil.selectedContacts){
                invitePhoneNumbers.put(member.phoneNumber)
              //  phoneAr.add(member.phoneNumber)
                var dt = HashMap<String,String>()
                dt["phonenumber"] = member.phoneNumber.replace(" ","")
                dt["role"] = member.role.toString()
                phoneAr.add(dt)
            }
            jsonGroupDetails["invites_phonenumbers_and_roles"] = phoneAr
            Timber.v("jsonGroupDetails.toJson()")
            Timber.v(jsonGroupDetails.toJson())
            CustomProgressBar.show(this.requireContext(),"Sending request")

            groupViewModel.createNewGroup(jsonGroupDetails)

        }

        //jsonGroupDetails.put("city",etCity.text.toString())
        //val jsonGroupType = JSONObject()
        //jsonGroupType.put("id",(DataUtil.groupTypeId).toInt())//DataUtil.groupTypeId
        //jsonGroupDetails.put("groupType",jsonGroupType)


        /*val jsonCreator = JSONObject()
        jsonCreator.put("id",SharedPrefenceUtil().getUserId(this.context!!))
        jsonGroupDetails.put("creator",jsonCreator)
        val jsonMemberArray = JSONArray()*/



        //entireJsonHolder.put("memberGroups",jsonGroupDetails)
        //entireJsonHolder.put("memberDetails",jsonMemberArray)
        Timber.v("jgroupdetaails %s",entireJsonHolder.toString())

    }

    private fun validInfo(): Boolean {
        if(etGroupName.text.toString().isEmpty() ){
            tlGroupName.error = "Group name is empty"
            return false
        }

        if(etTown.text.toString().isEmpty() ){
            tlTown.error = "Enter group  location"
            return false
        }
        if(etxtGroupDesc.text.toString().isEmpty() ){
            tlGroupDesc.error = "Enter group  description"
            return false
        }


        return true
    }


    /**
     * Loads group type when the fragment is launched on background
     */
    /*suspend fun populateGroupType() {
        mainViewModel.getGroupTypes().observe(this, Observer {
            when(it){
                is ViewModelWrapper.error -> Log.e("Error","Error occured")
                is ViewModelWrapper.response -> handleGroupTypeResponse(it.value)
            }
        })
    }*/

    private fun handleGroupTypeResponse(value: String) {
       // val gson = GsonBuilder().create()
        Timber.v("handleGroupTypeResponse %s",value)
        //groupTypeMmodelList = List<GroupTpeModel>()
      //  groupTypeMmodelList= gson.fromJson(value,Array<GroupTpeModel>::class.java).toList()

        val type = Types.newParameterizedType(MutableList::class.java, ScheduleType::class.java)
        val adapterRegion: JsonAdapter<List<ScheduleType>> = AppInfo.moshi.adapter(type)
         groupTypeMmodelList =   adapterRegion.fromJson(value)!!
        modelGroupList = groupTypeMmodelList
        for (countyItem in groupTypeMmodelList){
           // Log.e("listRegionjson",mList.get(0).name)
            groupTypeList.add(countyItem.name)
        }
    }

     fun populateCountry() {
        Timber.v("getting countries")
        mainViewModel.getCountries()
            .observe(viewLifecycleOwner, Observer { countries ->

//            when(it){
//                is ViewModelWrapper.error -> Log.e("Error","Error occured")
//                is ViewModelWrapper.response -> handleCountryResponse(it.value)
//            }
        })
       // val accountAdapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, Model) }
        //accountAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

    }

    private fun handleCountryResponse(value: String) {
        //val gson = GsonBuilder().create()
        Timber.v("handleCountryResponse %s",value)
        val type = Types.newParameterizedType(MutableList::class.java, Country::class.java)
        val adapterRegion: JsonAdapter<List<Country>> = AppInfo.moshi.adapter(type)
       var mList =   adapterRegion.fromJson(value)!!

        //val mList= gson.fromJson(value,Array<CountryModel>::class.java).toList()
        for (countyItem in mList){
            //Log.e("listRegionjson",mList.get(0).name)
            //countyList.add(countyItem.name)
        }
    }

     fun selectCountry() {
//        val accountAdapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, countyList) }
//        accountAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        AlertDialog.Builder(activity)
//            .setTitle("Select Country")
//            .setAdapter(accountAdapter) { dialog: DialogInterface, which: Int ->
//                etSelectCountry.setText(countyList.get(which))
//                dialog.dismiss()
//            }
//            .create()
//            .show()

    }

    /**
     * Handles when group type is clicked
     * params
     */
//    fun selectGroupType(){
//            val accountAdapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, groupTypeList) }
//            accountAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            AlertDialog.Builder(activity)
//                .setTitle("Select Group Type")
//                .setAdapter(accountAdapter) { dialog: DialogInterface, which: Int ->
//                    etGroupType.setText(modelGroupList.get(which).name)
//                    DataUtil.groupTypeId = modelGroupList.get(which).id
//                    Log.e("groupId",modelGroupList.get(which).id)
//                    dialog.dismiss()
//                }
//                .create()
//                .show()
//
//    }


}
