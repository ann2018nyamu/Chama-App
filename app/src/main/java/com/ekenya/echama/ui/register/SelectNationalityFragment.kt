package com.ekenya.echama.ui.register


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.ekenya.echama.R
import com.ekenya.echama.adapter.SelectNationalityAdapter
import com.ekenya.echama.inc.AppInfo
import com.ekenya.echama.model.Country
import com.ekenya.echama.viewModel.MainViewModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class SelectNationalityFragment : Fragment() {

    val mainVM by viewModels<MainViewModel>()

    lateinit var rcvNationality:RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView= inflater.inflate(R.layout.fragment_select_country, container, false)
        rcvNationality = rootView.findViewById(R.id.rcv_nationality)
        //get passed list

        var countryStr = arguments?.getString("countryStr")
        val type = Types.newParameterizedType(MutableList::class.java, Country::class.java)
        val adapterRegion: JsonAdapter<List<Country>> = AppInfo.moshi.adapter(type)
        var countries = adapterRegion.fromJson(countryStr)
        val nationalityAdapter = countries?.let { SelectNationalityAdapter(this, it) }
        rcvNationality.adapter = nationalityAdapter

        rcvNationality.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
//        mainVM.getCountries().observe(viewLifecycleOwner, Observer { countries ->
//            Timber.v("getCountries %s",countries.size)
//            countries.filter {
//                it.countryName.toLowerCase().contentEquals("kenya")
//                it.countryName.toLowerCase().contentEquals("ghana")
//                it.countryName.toLowerCase().contentEquals("angola")
//            }
//
//
//        })

        return rootView
    }

}
