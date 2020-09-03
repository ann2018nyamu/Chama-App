package com.ekenya.echama.ui.onboarding


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView
import androidx.navigation.fragment.findNavController

import com.ekenya.echama.R
import com.synnapps.carouselview.CarouselView
import org.w3c.dom.Text

/**
 * A simple [Fragment] subclass.
 */
class Onboarding : Fragment() {
lateinit var carouselView:CarouselView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_onboarding, container, false)
        //val btnGetStarted = view.fi
        carouselView = view.findViewById(R.id.carouselView)
        carouselView.pageCount = 4
        carouselView.setViewListener { position ->
            var v: View? = null
            if (position == 0) {
                v = inflater.inflate(R.layout.one_onboarding, null)

                val btnGetStarted: TextView = v.findViewById(R.id.tvSkip)
                 btnGetStarted.setOnClickListener {
                     findNavController().navigate(R.id.nav_landing)
                 }
            } else if (1 == position) {
                v = inflater.inflate(R.layout.three_onboarding, null)
                val btnGetStarted: TextView = v.findViewById(R.id.tvSkip)
                 btnGetStarted.setOnClickListener {
                     findNavController().navigate(R.id.nav_landing)
                 }
            } else if (2 == position) {
                v = inflater.inflate(R.layout.two_onboarding, null)
                val btnGetStarted: TextView = v.findViewById(R.id.tvSkip)
                btnGetStarted.setOnClickListener {
                    findNavController().navigate(R.id.nav_landing)
//                     findNavController().navigate(R.id.nav_home)
                }
            } else if (3 == position) {
                v = inflater.inflate(R.layout.four_onboarding, null)
                val btnGetStarted: TextView = v.findViewById(R.id.btnGetStarted)
                btnGetStarted.setOnClickListener {
                    findNavController().navigate(R.id.nav_landing)
//                     findNavController().navigate(R.id.nav_home)
                }
            }

                return@setViewListener v
        }

        return view
    }

}
