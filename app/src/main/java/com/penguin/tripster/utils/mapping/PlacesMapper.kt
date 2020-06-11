package com.penguin.tripster.utils.mapping

import com.penguin.tripster.BuildConfig
import com.penguin.tripster.model.PlaceOfInterest
import com.penguin.tripster.model.networkModel.NearbyPlacesResult
import com.penguin.tripster.utils.mapping.IMapper
import com.penguin.tripster.utils.mapping.INullableInputListMapperImpl

object PlacesMapper:
    IMapper<NearbyPlacesResult, PlaceOfInterest> {

    fun mapListArtObject(listResult: List<NearbyPlacesResult>?) : List<PlaceOfInterest> {
        return INullableInputListMapperImpl(
            this
        ).map(listResult)
    }

    override fun map(input: NearbyPlacesResult): PlaceOfInterest {
        val imageUrl = "${BuildConfig.BASE_URL}place/photo?key=${BuildConfig.API_KEY}&maxwidth=1080&photoreference=${input.photos?.get(0)?.photoReference ?: ""}"

        return PlaceOfInterest(
            input.placeId ?: "Unknown id",
            input.name ?: "Untitled",
            imageUrl,
            input.openingHours?.openNow,
            input.types ?: emptyList()
        )
    }
}