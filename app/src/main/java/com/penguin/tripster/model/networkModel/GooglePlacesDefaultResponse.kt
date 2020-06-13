package com.penguin.tripster.model.networkModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class GooglePlacesDefaultResponse {
    @SerializedName("html_attributions")
    @Expose
    var htmlAttributions: List<Any?>? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("next_page_token")
    @Expose
    var nextPageToken: String? = null

    @SerializedName("error_message")
    @Expose
    var errorMessage: String? = null

}