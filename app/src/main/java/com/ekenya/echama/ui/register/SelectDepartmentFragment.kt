package com.ekenya.echama.ui.register


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.ekenya.echama.R
import com.ekenya.echama.adapter.DepartmentAdapter

/**
 * A simple [Fragment] subclass.
 */
class SelectDepartmentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View= inflater.inflate(R.layout.fragment_select_department, container, false)
        //val passedRegionList = arguments?.getString("list")
        val rcvRegion=view.findViewById(R.id.rcv_department) as RecyclerView
        rcvRegion.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        //val regionList=  GsonBuilder().create().fromJson(passedRegionList,Array<RegionCallback>::class.java).toList()
        val adapter = DepartmentAdapter(this, RegisterStepTwo.regionObject!!.getDepartment())
        rcvRegion.adapter = adapter
        return view
    }


}
