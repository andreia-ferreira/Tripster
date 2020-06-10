package com.penguin.tripster.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "points_of_interest_table")
data class PlaceOfInterest(
    @PrimaryKey
    val placeId: String,
    val name: String,
    val image: String)