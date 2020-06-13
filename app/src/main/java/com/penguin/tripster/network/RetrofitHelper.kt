package com.penguin.tripster.network

import android.os.Build
import com.penguin.tripster.BuildConfig
import com.penguin.tripster.model.networkModel.NearbyPlacesResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitHelper {

    suspend fun getNearbyPlace(latLang: String, radius: Int, type: String): Response<NearbyPlacesResponse?> {
        return getClient().getNearbyPlaces(latLang, radius, type, BuildConfig.API_KEY)
    }

    suspend fun getNextNearbyPlaces(latLang: String, radius: Int, type: String, nextPageToken: String): Response<NearbyPlacesResponse?> {
        return getClient().getNextNearbyPlaces(latLang, radius, type, BuildConfig.API_KEY, nextPageToken)
    }

    private fun getClient(): IRetrofitClient {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    .build()
                chain.proceed(newRequest)
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(IRetrofitClient::class.java)
    }

}