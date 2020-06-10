package com.penguin.tripster.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.penguin.tripster.databinding.ItemNearbyPlaceBinding
import com.penguin.tripster.model.PlaceOfInterest
import androidx.databinding.library.baseAdapters.BR
import com.penguin.tripster.R

class NearbyPlacesRecyclerViewAdapter(private val context: Context, private val listPlaces: List<PlaceOfInterest>):
    RecyclerView.Adapter<NearbyPlacesRecyclerViewAdapter.PlacesViewHolder>() {

    lateinit var binding: ItemNearbyPlaceBinding

    inner class PlacesViewHolder(private val binding: ItemNearbyPlaceBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(place: PlaceOfInterest) {
            binding.setVariable(BR.place, place)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NearbyPlacesRecyclerViewAdapter.PlacesViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.item_nearby_place,
            parent,
            false)

        return PlacesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listPlaces.size
    }

    override fun onBindViewHolder(
        holder: NearbyPlacesRecyclerViewAdapter.PlacesViewHolder,
        position: Int
    ) {
        return holder.bind(listPlaces[position])
    }


}