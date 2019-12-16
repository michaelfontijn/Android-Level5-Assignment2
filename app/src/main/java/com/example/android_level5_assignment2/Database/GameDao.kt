package com.example.android_level5_assignment2.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.android_level5_assignment2.Models.Game

@Dao
interface GameDao{
    @Insert
    fun insertGame(game: Game)

    @Query("SELECT * FROM gameTable")
    fun getGames(): LiveData<List<Game>>

    @Delete
    fun deleteGame(game : Game)

}