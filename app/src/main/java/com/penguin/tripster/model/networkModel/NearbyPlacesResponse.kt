package com.penguin.tripster.model.networkModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NearbyPlacesResponse: GooglePlacesDefaultResponse() {
    @SerializedName("results")
    @Expose
    var results: List<NearbyPlacesResult?>? = null
}