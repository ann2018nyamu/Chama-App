package com.ekenya.echama.ui.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.ekenya.echama.R
import com.ekenya.echama.adapter.TrxAdapter
import com.ekenya.echama.model.Transaction

/**
 * A simple [Fragment] subclass.
 */
class RecentActivitiesFragment : Fragment() {
lateinit var rcvRecentactivity:RecyclerView
    lateinit var recentAdapter:TrxAdapter
    var activityList:ArrayList<Transaction> = ArrayList<Transaction>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView= inflater.inflate(R.layout.fragment_recent_activities, container, false) as View

        rcvRecentactivity = rootView.findViewById(R.id.rcvRecentActivity)
        rcvRecentactivity.layoutManager=LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        recentAdapter = TrxAdapter(this, activityList)
        rcvRecentactivity.adapter = recentAdapter
        return rootView
    }


}
