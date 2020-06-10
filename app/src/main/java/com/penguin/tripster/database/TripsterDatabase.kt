package com.penguin.tripster.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.penguin.tripster.model.PlaceOfInterest

@Database(entities = [PlaceOfInterest::class],
    version = 1,
    exportSchema = false)
abstract class TripsterDatabase: RoomDatabase() {

    abstract fun placesDao(): PlacesDao

    companion object {

        @Volatile
        private var INSTANCE: TripsterDatabase? = null

        fun getDatabase(context: Context): TripsterDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    TripsterDatabase::class.java,
                    "tripsterDatabase"
                ).build()
            }
        }
    }

}