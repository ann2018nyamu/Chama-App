package com.ekenya.echama.ui.onboarding


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

import com.ekenya.echama.R

/**
 * A simple [Fragment] subclass.
 */
class LandingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_landing, container, false)
        val btnsignup = view.findViewById(R.id.btnSignUp) as Button
        val btnLogin = view.findViewById(R.id.btnLogin) as Button
        btnsignup.setOnClickListener { findNavController().navigate(R.id.nav_register) }
        btnLogin.setOnClickListener { findNavController().navigate(R.id.to_login_fragment) }

        return view
    }


}
