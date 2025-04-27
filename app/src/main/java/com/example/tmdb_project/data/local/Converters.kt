package com.example.tmdb_project.data.local

import androidx.room.TypeConverter
import com.example.tmdb_project.ui.utils.WatchStatus

class Converters {
    @TypeConverter fun toStatus(value: String) = WatchStatus.valueOf(value)
    @TypeConverter fun fromStatus(status: WatchStatus) = status.name
}
