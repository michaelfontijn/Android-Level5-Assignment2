package com.example.android_level5_assignment2.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.android_level5_assignment2.Models.Game

@Dao
interface GameDao{
    @Insert
    suspend fun insertGame(game: Game)

    @Query("SELECT * FROM gameTable")
    fun getGames(): LiveData<List<Game?>>

    @Update
    suspend fun updateGame(note: Game)
}