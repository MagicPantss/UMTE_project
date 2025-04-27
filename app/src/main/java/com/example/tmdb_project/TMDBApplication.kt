package com.example.tmdb_project

import android.app.Application
import androidx.room.Room
import com.example.tmdb_project.data.local.AppDatabase

class TMDBApplication : Application() {
    companion object {
        lateinit var db: AppDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "tmdb_db"
        ).build()
    }
}