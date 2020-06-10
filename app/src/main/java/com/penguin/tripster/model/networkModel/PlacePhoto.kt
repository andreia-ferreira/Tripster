package com.penguin.tripster.model.networkModel

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class PlacePhoto {
    @SerializedName("height")
    @Expose
    val height: Int? = null

    @SerializedName("html_attributions")
    @Expose
    val htmlAttributions: List<String>? = null

    @SerializedName("photo_reference")
    @Expose
    val photoReference: String? = null

    @SerializedName("width")
    @Expose
    val width: Int? = null
}