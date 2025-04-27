package com.example.tmdb_project.data.local

import kotlinx.coroutines.flow.Flow
import androidx.room.*

@Dao
interface FavouriteDao {
    @Query("SELECT * FROM favourites")
    fun getAll(): Flow<List<FavouriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: FavouriteEntity)

    @Delete
    fun delete(entity: FavouriteEntity)
}
