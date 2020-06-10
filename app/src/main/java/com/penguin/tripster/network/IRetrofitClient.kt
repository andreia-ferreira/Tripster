package com.penguin.tripster.network

import com.penguin.tripster.model.networkModel.NearbyPlacesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IRetrofitClient {

    @GET("/maps/api/place/nearbysearch/json")
    suspend fun getNearbyPlaces(@Query("location", encoded = true) latLang: String,
                                @Query("radius") radius: Int,
                                @Query("type") placeType: String,
                                @Query("key") apiKey: String): Response<NearbyPlacesResponse?>

    @GET("/maps/api/place/nearbysearch/json")
    suspend fun getNextNearbyPlaces(@Query("location", encoded = true) latLang: String,
                                @Query("radius") radius: Int,
                                @Query("type") placeType: String,
                                @Query("key") apiKey: String,
                                @Query("nextPageToken") token: String): Response<NearbyPlacesResponse?>

    @GET("/maps/api/place/photo")
    suspend fun getPlacePhoto(@Query("photoreference") photoReference: String,
                                @Query("maxWidth") maxWidth: Int,
                                @Query("key") apiKey: String)

    @GET("/maps/api/place/details/json")
    suspend fun getPlaceDetails(@Query("placeId") placeId: String,
                              @Query("key") apiKey: String)

}