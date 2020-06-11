package com.penguin.tripster.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.penguin.tripster.R
import com.penguin.tripster.databinding.ItemNearbyPlaceBinding
import com.penguin.tripster.databinding.ItemSpotlightNearbyPlaceBinding
import com.penguin.tripster.model.PlaceOfInterest

class NearbyPlacesRecyclerViewAdapter(private val context: Context, private val listPlaces: List<PlaceOfInterest>):
    RecyclerView.Adapter<NearbyPlacesRecyclerViewAdapter.NearbyPlacesViewHolder>() {

    inner class NearbyPlacesViewHolder(private val binding: ItemNearbyPlaceBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(place: PlaceOfInterest) {
            binding.setVariable(BR.place, place)
            binding.setVariable(BR.types, place.types?.joinToString()?.replace("_", " "))

            when (place.openNow) {
                null -> {
                    binding.setVariable(BR.openNowText, context.getString(R.string.text_unknown_opening))
                    binding.setVariable(BR.openNowColor, context.resources.getColor(R.color.unknownColor))
                }
                true -> {
                    binding.setVariable(BR.openNowText, context.getString(R.string.text_open))
                    binding.setVariable(BR.openNowColor, context.resources.getColor(R.color.openColor))
                }
                false -> {
                    binding.setVariable(BR.openNowText, context.getString(R.string.text_closed))
                    binding.setVariable(BR.openNowColor, context.resources.getColor(R.color.closedColor))
                }
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NearbyPlacesViewHolder {

        val binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.item_nearby_place,
            parent,
            false) as ItemNearbyPlaceBinding

        return NearbyPlacesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listPlaces.size
    }

    override fun onBindViewHolder(
        holder: NearbyPlacesViewHolder,
        position: Int
    ) {
        return holder.bind(listPlaces[position])

    }

}