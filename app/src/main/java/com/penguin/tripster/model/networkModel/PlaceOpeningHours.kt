package com.penguin.tripster.model.networkModel

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class PlaceOpeningHours {
    @SerializedName("open_now")
    @Expose
    val openNow: Boolean? = null
}