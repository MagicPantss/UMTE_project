package com.example.tmdb_project.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.tmdb_project.api.RetrofitInstance
import com.example.tmdb_project.data.UpcomingMovie
import com.example.tmdb_project.data.UpcomingResponse
import java.time.LocalDate
import android.util.Log

class UpcomingWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        Log.d("UpcomingWorker", "🚀 doWork() spuštěn")

        return try {
            val response: UpcomingResponse = RetrofitInstance.api.getUpcoming()

            val today = LocalDate.now()

            val nextMovie: UpcomingMovie? = response.results
                .mapNotNull { movie ->
                    runCatching { LocalDate.parse(movie.releaseDate) }
                        .getOrNull()
                        ?.let { date -> movie to date }
                }
                .filter { (_, date) -> date.isAfter(today) }
                .minByOrNull { it.second }
                ?.first

            nextMovie?.let { movie ->
                showNotification(
                    movieId = movie.id,
                    title = "❯ Premiéra: ${movie.title}",
                    content = "Vyjde: ${movie.releaseDate}"
                )
                Log.d("UpcomingWorker", "Notifikace odeslána pro: ${movie.title} (${movie.releaseDate})")
            } ?: Log.d("UpcomingWorker", "Žádné nadcházející filmy po dnešku")

            Result.success()
        } catch (e: Exception) {
            Log.e("UpcomingWorker", "Chyba v doWork(): ${e.message}", e)
            Result.retry()
        }
    }

    private fun showNotification(movieId: Int, title: String, content: String) {
        val nm = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "upcoming_channel",
                "Nadcházející filmy",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            nm.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, "upcoming_channel")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(content)
            .setAutoCancel(true)
            .build()

        nm.notify(movieId, notification)
    }
}
