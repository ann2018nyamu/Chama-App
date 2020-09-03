package com.ekenya.echama.ui.login


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.ekenya.echama.viewModel.MainViewModel
import com.ekenya.echama.viewModel.ViewModelWrapper
import com.ekenya.echama.R

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class GroupInviteFragment : Fragment() {
    lateinit var edtUsername: EditText
    lateinit var edtPassword: EditText
    lateinit var mainViewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView= inflater.inflate(R.layout.fragment_group_invite, container, false)
        edtUsername = rootView.findViewById(R.id.etEmailPhoneNumber)
        edtPassword = rootView.findViewById(R.id.etPassword)
        edtUsername.setText("728844034")
        edtPassword.setText("1234")
        val btnLogin = rootView.findViewById(R.id.btnLogin) as Button
        btnLogin.setOnClickListener {
            Timber.v("btnLogin")
//            if (validDateails()) {
//                val username = edtUsername.text.toString()
//                val password = edtPassword.text.toString()
//                val jsonVerification = JSONObject()
//                jsonVerification.put("username", username)
//                jsonVerification.put("password", password)
//                GlobalScope.launch(Dispatchers.Main) {
//                    userLogin(jsonVerification)
//                }
//            }
        }
        return rootView
    }

//    suspend fun userLogin(jsonVerification: JSONObject) {
//        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
     //   mainViewModel.userlogin(jsonVerification)
//        mainViewModel.userlogin(jsonVerification).observe(this, Observer {
//
//            when(it){
//                is ViewModelWrapper.error -> Log.e("apierror",it.error)
//                is ViewModelWrapper.response ->processResponse(it.value)
//            }
//        })
//    }

    private fun processResponse(jsonData: String) {
//        val gson = GsonBuilder().create()
//        val regionList= gson.fromJson(jsonData, JsonElement::class.java)
//
//        // val name = regionList.asJsonObject.get("id")
//        val name = regionList.asJsonObject.get("access_token").asJsonObject.get("token")
//        Timber.v("passchange %s",name.toString())
//        Toast.makeText(this.activity,"Password updated succesfuly", Toast.LENGTH_SHORT)
    }

    private fun validDateails(): Boolean {

        return true
    }


}





