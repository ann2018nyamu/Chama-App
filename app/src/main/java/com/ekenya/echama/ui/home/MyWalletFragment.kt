package com.ekenya.echama.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ekenya.echama.MainActivity
import com.ekenya.echama.R
import com.ekenya.echama.adapter.TrxAdapter
import com.ekenya.echama.databinding.FragmentMywalletBinding
import com.ekenya.echama.model.Transaction
import com.ekenya.echama.util.*
import com.ekenya.echama.viewModel.GroupViewModel
import com.ekenya.echama.viewModel.MainViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import okhttp3.internal.notifyAll
import org.json.JSONObject
import timber.log.Timber

class MyWalletFragment : Fragment(), View.OnClickListener {

    lateinit var myWalletVM: MainViewModel
    lateinit var groupViewModel: GroupViewModel
//    lateinit var rcvRecentActivity : RecyclerView
    lateinit var recentAdapter:TrxAdapter
    var activityList:ArrayList<Transaction> = ArrayList()
    //stores number of failed unlocking trials
    var errorTrials:Int = 0
    var page:Int = 0
    var size:Int = 100
    private var _binding: FragmentMywalletBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMywalletBinding.inflate(inflater, container, false)
        val root = binding.root
      //  val root = inflater.inflate(R.layout.fragment_mywallet, container, false)
        groupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java)
        myWalletVM = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.imgLock.setOnClickListener(this)
        binding.imgUnLock.setOnClickListener(this)
        binding.txtUnlockBalance.setOnClickListener(this)
        binding.btnFullStatement.setOnClickListener(this)

        initData()
        initRV()
        initButton()
        unlockBalance()
        return root
        
    }
    private fun unlockBalance() {
        if( binding.clWalletUnlock.visibility == View.GONE){
            binding.clWalletLock.visibility = View.GONE
            binding.clWalletUnlock.visibility = View.VISIBLE
        }else{
            binding.clWalletLock.visibility = View.VISIBLE
            binding.clWalletUnlock.visibility = View.GONE
        }
    }

    private fun initButton() {
        binding.btnWithdraw.setOnClickListener{
            showWithdrawDialogue()
        }

        binding.btnLoans.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("user",   myWalletVM.currentUser.toJson())
            Timber.v(myWalletVM.currentUser.toJson())
            findNavController().navigate(R.id.nav_myloans,bundle)
        }

        binding.btnDeposit.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("action","wallet")
            findNavController().navigate(R.id.nav_makepayment,bundle)
        }
    }
    fun showWithdrawDialogue(){
        val builder = AlertDialog.Builder(this.requireContext())
        val mDialogView = LayoutInflater.from(this.requireContext()).inflate(R.layout.dialogue_userwithdraw_layout, null)
        builder.setView(mDialogView)
        //set title for alert dialog
        //builder.setTitle("In development")

        var tlAmount: TextInputLayout =  mDialogView.findViewById(R.id.tlAmount)
        var tlReason: TextInputLayout =  mDialogView.findViewById(R.id.tlReason)
        var etxtAmount: EditText =  mDialogView.findViewById(R.id.etxtAmount)
        var etxtReason: EditText =  mDialogView.findViewById(R.id.etxtReason)
        var btnCancel: MaterialButton =  mDialogView.findViewById(R.id.btnCancel)
        var btnWithdraw: MaterialButton =  mDialogView.findViewById(R.id.btnWithdraw)

        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()

        btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }
        btnWithdraw.setOnClickListener {
            if( etxtAmount.text.toString().isEmpty()){
                tlAmount.error = "Enter Amount"
            }else if(etxtAmount.text.toString().toDouble() > myWalletVM.currentUser.walletbalance!!){
                tlAmount.error = "You don't have sufficient amount to withdraw"
            }else{
                var reason = "User withdrawal"
                if( etxtAmount.text.toString().isNotEmpty()){
                    reason = etxtAmount.text.toString()
                }
                val withdrawData = HashMap<String, Any>()
                withdrawData["amount"] =  etxtAmount.text.toString().toDouble()
                withdrawData["creditaccount"] = myWalletVM.currentUser.phonenumber
                withdrawData["withdrawalreason"] = reason
                myWalletVM.userWithdraw(withdrawData)
            }
        }
        // Set other dialog properties
        alertDialog.setCancelable(true)
        alertDialog.show()
    }

    private fun initRV() {
        binding.rcvRecentActivity.layoutManager= LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        recentAdapter = TrxAdapter(this, activityList)
        binding.rcvRecentActivity.adapter = recentAdapter
    }

    private fun initData(){
        binding.txtWalletBalance.text =  myWalletVM.currentUser.getFWalletBalance()
        myWalletVM.getUserDetails()

        myWalletVM.getUserTransactions(page,size)
        myWalletVM.userLD.observe(viewLifecycleOwner, Observer {users ->
            Timber.v("users size"+users.size)
            if(users.isNotEmpty()){
                myWalletVM.currentUser = users[0]
                binding.txtWalletBalance.text =  myWalletVM.currentUser.getFWalletBalance()
            }
        })

        myWalletVM.myApiResponseLD.observe(viewLifecycleOwner, Observer {
            Timber.v("myApiResponseLD ${it.requestName} ${it.code} ${it.message}")
            if(it.code == 200){
                if(it.requestName.contentEquals("userWithdrawRequest")){
                    successAlert("Success","Your Withdrawal request has started",false)
                }

                if(it.requestName.contentEquals("getUserTrxRequest")){
                  var activityListAr =   it.responseObj as ArrayList<Transaction>
                    Timber.v("activityListAr size"+activityListAr.size)
                    if(activityListAr.isNotEmpty()){
                        activityList = activityListAr
                      //  activityList.addAll(activityListAr)
                        initRV()
                        binding.txtNoTrxDesc.visibility = View.GONE
                    }else{
                        binding.txtNoTrxDesc.visibility = View.VISIBLE
                    }
                }
            }else{
                if(it.message.isNotEmpty()) {
                    ToastnDialog.toastError(this.requireContext(), it.message)
                }
            }
            CustomProgressBar.hide()
        })
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

    override fun onClick(v: View?) {
        if (v != null) {
            if( (v.id == R.id.btnFullStatement)){
                //Todo fetching  user full statement
                val bundle = Bundle();
                bundle.putString("action","userstatement")
               findNavController().navigate(R.id.nav_fullstatement,bundle)
            }
            if( (v.id == R.id.imgLock)){
                unlockBalance()
            }
            if( (v.id == R.id.imgUnLock) || (v.id == R.id.imgUnLock)){
                showUnlockDialogue()
            }
        }
    }
     fun showUnlockDialogue(title:String = "",message:String = ""){
         val builder = AlertDialog.Builder(this.requireContext())
         val mDialogView = LayoutInflater.from(this.requireContext()).inflate(R.layout.dialogue_layout, null)
         builder.setView(mDialogView)
        //set title for alert dialog
        //builder.setTitle("In development")

        //set message for alert dialog
        // builder.setMessage("Do you want to log out ")
        //builder.setIcon(android.R.drawable.ic_dialog_alert)
        //performing positive action

//        builder.setPositiveButton("Okay"){dialogInterface, _ ->
//            dialogInterface.dismiss()
//        }
//        builder.setNegativeButton("Cancel"){ dialogInterface, which ->
//            dialogInterface.dismiss()
//        }

         var tvDialogTitle: TextView =  mDialogView.findViewById(R.id.tvDialogTitle)
         var tvDialogMessage: TextView =  mDialogView.findViewById(R.id.tvDialogMessage)
         var tlPin: TextInputLayout =  mDialogView.findViewById(R.id.tlPin)
         var etxtPin: EditText =  mDialogView.findViewById(R.id.etxtPin)
         var btnReject: MaterialButton =  mDialogView.findViewById(R.id.btnReject)
         var btnApprove: MaterialButton =  mDialogView.findViewById(R.id.btnApprove)
         tvDialogTitle.text = "Enter Pin"
         tvDialogMessage.text = "Enter your pin to view balance"
         btnReject.text = "Cancel"
         btnApprove.text = "Proceed"

        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()

         btnReject.setOnClickListener {
             alertDialog.dismiss()
         }
         btnApprove.setOnClickListener {
             if( etxtPin.text.length < 4){
                 tlPin.error = "Invalid Pin"
             }
             //Todo confirm with user encrypted pin
            // Timber.v("cpass "+myWalletVM.currentUser.pass)
            // Timber.v("mypass "+etxtPin.text.toString().getHashedPin256())
             if(errorTrials == 3){
                 alertDialog.dismiss()
                 expiredTokenDialogue()
             }else {

                 if (etxtPin.text.toString().getHashedPin256().contentEquals(myWalletVM.currentUser.pass.toString().getHashedPin256())
                     || etxtPin.text.toString().contentEquals("1234") //Todo remove this line on production
                 ){

                     unlockBalance()
                     alertDialog.dismiss()
                 } else {
                     errorTrials += 1
                     tlPin.error = "Incorrect Pin. ${4 - errorTrials } trials remaining"
                 }
             }

         }
        // Set other dialog properties

        alertDialog.setCancelable(true)
        alertDialog.show()
    }


}