package com.example.tmdb_project

import android.app.Application
import androidx.room.Room
import com.example.tmdb_project.data.local.AppDatabase
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import com.example.tmdb_project.notification.UpcomingWorker

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

        scheduleUpcomingNotifications()
    }

    private fun scheduleUpcomingNotifications() {
        val workRequest = PeriodicWorkRequestBuilder<UpcomingWorker>(
            2, TimeUnit.MINUTES
        )
            .setInitialDelay(0, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(applicationContext)
            .enqueueUniquePeriodicWork(
                "UpcomingMovieNotifications",
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
    }
}