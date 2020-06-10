package com.penguin.tripster.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.penguin.tripster.model.PlaceOfInterest

@Dao
interface PlacesDao {

    @Query("SELECT * FROM points_of_interest_table")
    suspend fun getNearbyPlaces(): List<PlaceOfInterest>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNearbyPlaces(list: List<PlaceOfInterest>)

    @Query("DELETE FROM points_of_interest_table")
    suspend fun deleteAllPlaces()

}