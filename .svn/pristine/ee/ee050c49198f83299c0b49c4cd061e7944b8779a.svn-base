package com.ekenya.echama.ui.register


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ekenya.echama.R
import com.ekenya.echama.adapter.RegionAdapter
import com.ekenya.echama.inc.AppInfo.Companion.moshi
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types


/**
 * A simple [Fragment] subclass.
 */
class SelectRegionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View= inflater.inflate(R.layout.fragment_select_region, container, false)
        val passedRegionList = arguments?.getString("list")

        val rcvRegion=view.findViewById(R.id.rcv_region) as RecyclerView
        rcvRegion.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        val type = Types.newParameterizedType(
                            MutableList::class.java,
            RegionCallback::class.java
                        )
                        val adapterRegion: JsonAdapter<List<RegionCallback>> = moshi.adapter(type)
        var regionList =   adapterRegion.fromJson(passedRegionList)!!

        val adapter = RegionAdapter(regionList,this)
        rcvRegion.adapter = adapter


        return view
    }


}
