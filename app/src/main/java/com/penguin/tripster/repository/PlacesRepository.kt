package com.penguin.tripster.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.penguin.tripster.BuildConfig
import com.penguin.tripster.R
import com.penguin.tripster.database.PlacesDao
import com.penguin.tripster.model.PlaceOfInterest
import com.penguin.tripster.model.networkModel.NearbyPlacesResponse
import com.penguin.tripster.model.networkModel.NearbyPlacesResult
import com.penguin.tripster.network.RetrofitHelper
import retrofit2.Response
import java.net.UnknownHostException

class PlacesRepository(private val mApplication: Application,
                       private val dao: PlacesDao,
                       private val retrofitHelper: RetrofitHelper) {

    val reposErrors = MutableLiveData<String>()
    val listToDisplay = MutableLiveData<List<PlaceOfInterest>>()
    private val networkList = mutableListOf<PlaceOfInterest>()
    private var nextPageToken = MutableLiveData<String?>()

    suspend fun refreshPlaces(reset: Boolean, latLang: String, radius: Int, type: String): Boolean {
        try {
            val response = if (reset) {
                loadInitialPlaces(latLang, radius, type)
            } else {
                loadMorePlaces(latLang, radius, type)
            }

            if (response.isSuccessful) {
                val listPlaces = response.body()?.results ?: emptyList()
                nextPageToken.value = response.body()?.nextPageToken

                if (listPlaces.isNotEmpty()) {
                    Log.d(TAG, "Found ${listPlaces.size} results")

                    for (place: NearbyPlacesResult? in listPlaces) {
                        place?.let {
                            //TODO mapper
                            val imageUrl = "${BuildConfig.BASE_URL}place/photo?key=${BuildConfig.API_KEY}&maxwidth=1080&photoreference=${it.photos?.get(0)?.photoReference ?: ""}"
                            val placeOfInterest = PlaceOfInterest(it.placeId ?: "", it.name ?: "", imageUrl)
                            networkList.add(placeOfInterest)
                        }
                    }
                    dao.insertNearbyPlaces(networkList)
                    reposErrors.value = null
                } else {
                    reposErrors.value = response.body()?.errorMessage ?: mApplication.getString(R.string.error_empty_search_places)
                }
            }
        } catch (e: UnknownHostException) {
            reposErrors.value = mApplication.getString(R.string.error_service_call)
        } catch (e: Exception) {
            Log.e(TAG, "$e: ${e.message}")

        } finally {
            return true
        }
    }

    private suspend fun loadInitialPlaces(latLang: String, radius: Int, type: String): Response<NearbyPlacesResponse?> {
        val response = retrofitHelper.getNearbyPlace(latLang, radius, type)

        if (response.isSuccessful) {
            clearCache()
        }

        return response
    }

    private suspend fun loadMorePlaces(latLang: String, radius: Int, type: String): Response<NearbyPlacesResponse?> {
        if (nextPageToken.value == null) throw Exception("End")
        return retrofitHelper.getNextNearbyPlaces(latLang, radius, type, nextPageToken.value!!)
    }

    suspend fun getStoredPlaces() {
        try {
            listToDisplay.value = dao.getNearbyPlaces()
            Log.d(TAG, "Fetched ${listToDisplay.value?.size} items")

        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
        }
    }

    private suspend fun clearCache() {
        dao.deleteAllPlaces()
        listToDisplay.value = emptyList()
        networkList.clear()
    }

    companion object {
        private val TAG = PlacesRepository::class.java.simpleName

        // For Singleton instantiation
        @Volatile
        private var instance: PlacesRepository? = null

        fun getInstance(application: Application, dao: PlacesDao, retrofitHelper: RetrofitHelper) =
            instance ?: synchronized(this) {
                instance ?: PlacesRepository(application, dao, retrofitHelper).also { instance = it }
            }
    }

}