package com.ekenya.echama.ui.onboarding


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.ekenya.echama.R
import kotlinx.coroutines.*

/**
 * A simple [Fragment] subclass.
 */
class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root:View= inflater.inflate(R.layout.fragment_splash, container, false)

        GlobalScope.launch(Dispatchers.Main) {
           // delay(2000)

            findNavController().navigate(R.id.to_onboarding_navigation)
        }
        return root
    }


}
