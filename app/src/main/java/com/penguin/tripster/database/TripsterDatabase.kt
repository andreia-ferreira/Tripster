package com.penguin.tripster.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.penguin.tripster.model.PlaceOfInterest
import com.penguin.tripster.utils.Converters

@Database(entities = [PlaceOfInterest::class],
    version = 1,
    exportSchema = false)
@TypeConverters(Converters::class)
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