package com.ekenya.echama.ui.group.contribution


import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ekenya.echama.MainActivity
import com.ekenya.echama.R
import com.ekenya.echama.adapter.CPenaltiesAdapter
import com.ekenya.echama.adapter.GroupInvitesAdapter
import com.ekenya.echama.databinding.FragmentCreateContributionBinding
import com.ekenya.echama.databinding.FragmentPenaltyBinding
import com.ekenya.echama.model.*
import com.ekenya.echama.util.*
import com.ekenya.echama.viewModel.GroupViewModel
import com.ekenya.echama.viewModel.MainViewModel
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.properties.Delegates


/**
 * A simple [Fragment] subclass.
 */
class CPenaltyFragment : Fragment() {
    private var mypenaltyamount: Double = 0.00
    private lateinit var mainViewModel: MainViewModel
    private var cPenaltyLIst: ArrayList<GroupPenalty> = ArrayList()
    private lateinit var contributionname: String
    private lateinit var cPenaltyAdapter: CPenaltiesAdapter
    internal var myCalendar = Calendar.getInstance()

    private var _binding: FragmentPenaltyBinding? = null
    private val binding get() = _binding!!
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH)
    val penaltyDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.ENGLISH)
    lateinit var groupViewModel: GroupViewModel
    private var contributionid: Int = 0
    private var mypenalty: GroupPenalty = GroupPenalty("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        groupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        _binding = FragmentPenaltyBinding.inflate(inflater, container, false)
        val rootView = binding.root
       // val btnSave = rootView.findViewById(R.id.btnSave) as Button
        contributionid = 1
        contributionname = "Corona Research Fund"

        arguments?.getString("contributionid")?.let {
            contributionid = it.toInt()
        }
        arguments?.getString("contributionname")?.let {
            contributionname = it
        }
        initData()
        initRV()
        initButton()

        return rootView
    }

    private fun initButton() {
        Timber.v("MainActivity.mainVM.currentUser.phonenumber")
        Timber.v(mainViewModel.currentUser.phonenumber)
        mainViewModel.userLD.observe(viewLifecycleOwner, Observer { users ->
            Timber.v("user "+users.size)
            if(users.isNotEmpty()){
                mainViewModel.currentUser = users[0]
                Timber.v(MainActivity.mainVM.currentUser.phonenumber)
                //Timber.v("usertoken "+users[0].access_token)
            }
        })
        binding.btnPay.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("action","penalty")
            bundle.putString("amount",mypenaltyamount.toString())
            bundle.putString("penalty",mypenalty.toJson())
            findNavController().navigate(R.id.nav_makepayment,bundle)
        }

    }

    private fun populateData() {
        Glide.with(this)
            .asBitmap()
            .placeholder(resources.getDrawable(R.mipmap.group_profile))
            .load(mypenalty.memberPicUrl)
            .into(binding.ivMemberPic)

            binding.tvMemberName.text = mypenalty.memberName
            binding.tvPenaltyCurrency.text = DataUtil.gcurrency
            binding.tvPenaltyAmount.text = mypenalty.getFPenaltyAmount()
            mypenaltyamount = mypenalty.penaltyAmount!!

    }

    private fun initRV(){
        binding.rcvContributionPenalty.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        cPenaltyAdapter = CPenaltiesAdapter(this,cPenaltyLIst)
        binding.rcvContributionPenalty.adapter = cPenaltyAdapter
    }


    private fun initData() {
        val jsonGroupDetails = HashMap<String,Any>()
        jsonGroupDetails["contributionid"] = contributionid
        jsonGroupDetails["page"] = 0
        jsonGroupDetails["size"] = 100
        groupViewModel.getCPenalties(jsonGroupDetails)


        groupViewModel.myGroupApiResponseLD.observe(viewLifecycleOwner, Observer { myApiResponse ->
            CustomProgressBar.hide()
            initButton()
            Timber.v("myApiResponseLD  ${myApiResponse.code} ${myApiResponse.requestName} ${myApiResponse.message}")
            if(myApiResponse.code == 200){

                if(myApiResponse.requestName.contentEquals("getGroupAccountsRequest")) {
                    successAlert("Success",myApiResponse.message,false)
                }

                if(myApiResponse.requestName.contentEquals("getCPenaltiesRequest")) {
                    cPenaltyLIst = myApiResponse.responseObj as ArrayList<GroupPenalty>
                    for(penalty in cPenaltyLIst){
                        Timber.v("mainViewModel "+mainViewModel.currentUser.phonenumber)
                       if(penalty.memberphone?.contentEquals(mainViewModel.currentUser.phonenumber)!!){
                           mypenalty = penalty
                           populateData()
                           binding.myPenaltyCL.visibility = View.VISIBLE
                       }
                    }

                    Timber.v("getCTrxRequest ${cPenaltyLIst.size}")
                    if( cPenaltyLIst.isEmpty()){
                        Timber.v("binding.txtNoTrxDesc.visibility VISIBLE")
                        ToastnDialog.toastSuccess(this.requireContext(),myApiResponse.message)
                    }else{
                        initRV()
                        Timber.v("binding.txtNoTrxDesc.visibility GONE")
                    }

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



    private fun handleRequestResponse(value: String) {
        Timber.v(value)
        //USED GROUP TYPE MODEL SINCE THEY ARE HAVING SAME RESPOSE JSON
       // val mList= gson.fromJson(value,Array<JsonElement>::class.java).toList()

        //Return back to home fragment
       // ResponseWrapper().getApiResponse()
        ToastnDialog.toastSuccess(this.context!!,"Request was Successful")
        findNavController().navigateUp()
    }

}
