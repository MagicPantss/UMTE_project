package com.example.tmdb_project.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tmdb_project.ui.utils.WatchStatus

@Database(
    entities = [FavouriteEntity::class, WatchlistEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favouriteDao(): FavouriteDao
    abstract fun watchlistDao(): WatchlistDao
}