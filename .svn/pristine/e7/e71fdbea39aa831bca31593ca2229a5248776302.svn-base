package com.ekenya.echama.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ekenya.echama.R
import com.ekenya.echama.model.Country
import com.ekenya.echama.ui.register.RegisterStepTwo

class SelectNationalityAdapter(val context: Fragment, val countList:List<Country>):RecyclerView.Adapter<SelectNationalityAdapter.CountryViewHolder>() {

    inner class CountryViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
       // val tvCountryName =itemView.findViewById(R.id.tvregionName) as TextView
        val tvCountryName =itemView.findViewById(R.id.tvCountryName) as TextView
        val ivCountryImageUrl = itemView.findViewById(R.id.ivCountryFlag) as ImageView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_region,parent,false))
    }

    override fun getItemCount(): Int {
       return countList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.tvCountryName.setText(countList[position].countryName)
        Glide.with(context)
            .asBitmap()
            .load("https://demo-api.ekenya.co.ke/chama/mobile/req/countries/"+countList.get(position).countryCode)
            .into(holder.ivCountryImageUrl)

        holder.itemView.setOnClickListener {
            RegisterStepTwo.nationalityName = countList[position].countryName
            RegisterStepTwo.countrycode = countList[position].countryCode
            RegisterStepTwo.dialcode = countList[position].dialCode

            context.findNavController().navigateUp()
        }
    }


}