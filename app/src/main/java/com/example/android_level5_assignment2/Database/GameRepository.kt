package com.example.android_level5_assignment2.Database

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.android_level5_assignment2.Models.Game

class GameRepository(context: Context) {

    private val gameDao: GameDao
    init {
        val database = GameRoomDatabase.getDatabase(context)
        gameDao = database!!.gameDao()
    }

    fun getGames(): LiveData<List<Game>> {
        return gameDao.getGames()
    }

    fun createGame(game : Game){
         gameDao.insertGame(game)
    }

    fun deleteGame(game : Game){
        gameDao.deleteGame(game)
    }

    fun deleteGames(){

    }

}