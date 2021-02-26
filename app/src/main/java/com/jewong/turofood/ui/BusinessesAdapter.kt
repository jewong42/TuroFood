package com.jewong.turofood.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jewong.turofood.R
import com.jewong.turofood.api.data.Business
import com.jewong.turofood.api.data.Businesses
import com.jewong.turofood.databinding.ViewholderBusinessBinding
import com.squareup.picasso.Picasso

class BusinessesAdapter : RecyclerView.Adapter<BusinessesAdapter.ViewHolder>() {

    private val dataSet: MutableList<Business> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.viewholder_business, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val business = dataSet[position]
        holder.binding.apply {
            businessName.text = business.name
            businessRating.rating = business.rating.toFloat()
            businessCategories.text = getCategories(business)
            Picasso.get().load(business.image_url).into(businessThumbnail)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun addToList(businesses: Businesses) {
        dataSet.addAll(businesses.businesses)
        notifyDataSetChanged()
    }

    private fun getCategories(categories: Business): String {
        val builder = StringBuilder()
        for (n in categories.categories.indices) {
            builder.append(categories.categories[n].title)
            if (n + 1 != categories.categories.size) builder.append(", ")
        }
        return builder.toString()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: ViewholderBusinessBinding = ViewholderBusinessBinding.bind(view)
    }

}
