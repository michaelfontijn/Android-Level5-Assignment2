package com.example.android_level5_assignment2.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android_level5_assignment2.Database.GameRepository
import com.example.android_level5_assignment2.Models.Game
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val gameRepository = GameRepository(application.applicationContext)
    private val mainScope = CoroutineScope(Dispatchers.Main)

    val games :  LiveData<List<Game>> = gameRepository.getGames()
    val error = MutableLiveData<String?>()
    val message = MutableLiveData<String?>()

    fun deleteGame(game :Game){
        mainScope.launch {
            withContext(Dispatchers.IO) {
                gameRepository.deleteGame(game)
            }
            message.value = "Game was successfully removed!"
        }
    }

    fun getGames(){
        mainScope.launch {
            withContext(Dispatchers.IO) {
              games.value.apply { gameRepository.getGames() }
            }
        }
    }

}