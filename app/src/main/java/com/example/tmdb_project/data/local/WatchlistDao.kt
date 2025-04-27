package com.example.tmdb_project.data.local

import kotlinx.coroutines.flow.Flow
import androidx.room.*
import com.example.tmdb_project.ui.utils.WatchStatus

@Dao
interface WatchlistDao {
    @Query("SELECT * FROM watchlist")
    fun getAll(): Flow<List<WatchlistEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: WatchlistEntity)

    @Delete
    fun delete(entity: WatchlistEntity)

    @Query("UPDATE watchlist SET status = :newStatus WHERE id = :id")
    fun updateStatus(id: Int, newStatus: WatchStatus): Int
}