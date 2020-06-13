package com.penguin.tripster.model.networkModel

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class PlaceGeometry {
    @SerializedName("location")
    @Expose
    val location: Location? = null

    @SerializedName("viewport")
    @Expose
    val viewport: Viewport? = null
}

class Location {
    @SerializedName("lat")
    @Expose
    val lat: Double? = null

    @SerializedName("lng")
    @Expose
    val lng: Double? = null
}

class Viewport {
    @SerializedName("northeast")
    @Expose
    val northeast: Northeast? = null

    @SerializedName("southwest")
    @Expose
    val southwest: Southwest? = null
}

class Southwest {
    @SerializedName("lat")
    @Expose
    val lat: Double? = null

    @SerializedName("lng")
    @Expose
    val lng: Double? = null
}

class Northeast {
    @SerializedName("lat")
    @Expose
    val lat: Double? = null

    @SerializedName("lng")
    @Expose
    val lng: Double? = null
}
