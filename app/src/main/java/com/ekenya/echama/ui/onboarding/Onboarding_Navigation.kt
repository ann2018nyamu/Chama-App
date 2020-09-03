package com.ekenya.echama.ui.onboarding


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ekenya.echama.R

/**
 * A simple [Fragment] subclass.
 */
class Onboarding_Navigation : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View= inflater.inflate(R.layout.fragment_onboarding_navigation, container, false)

        return view
    }


}
