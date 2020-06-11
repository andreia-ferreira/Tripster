package com.penguin.tripster.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.penguin.tripster.model.PlaceOfInterest
import androidx.databinding.library.baseAdapters.BR
import com.penguin.tripster.R
import com.penguin.tripster.databinding.ItemSpotlightNearbyPlaceBinding

class SpotlightPlacesRecyclerViewAdapter(private val context: Context, private val listPlaces: List<PlaceOfInterest>):
    RecyclerView.Adapter<SpotlightPlacesRecyclerViewAdapter.SpotlightPlacesViewHolder>() {

    inner class SpotlightPlacesViewHolder(private val binding: ItemSpotlightNearbyPlaceBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(place: PlaceOfInterest) {
            binding.setVariable(BR.place, place)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SpotlightPlacesViewHolder {

        val binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.item_spotlight_nearby_place,
            parent,
            false) as ItemSpotlightNearbyPlaceBinding

        return SpotlightPlacesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listPlaces.size
    }

    override fun onBindViewHolder(
        holder: SpotlightPlacesViewHolder,
        position: Int
    ) {
        return holder.bind(listPlaces[position])

    }

}