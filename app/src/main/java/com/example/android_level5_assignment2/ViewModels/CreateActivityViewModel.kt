package com.example.android_level5_assignment2.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.android_level5_assignment2.Database.GameRepository
import com.example.android_level5_assignment2.Models.Game
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val gameRepository = GameRepository(application.applicationContext)
    private val mainScope = CoroutineScope(Dispatchers.Main)

    val game = MutableLiveData<Game?>()
    val error = MutableLiveData<String?>()
    val success = MutableLiveData<Boolean>()

    fun createGame() {
        if (isNoteValid()) {
            mainScope.launch {
                withContext(Dispatchers.IO) {
                    gameRepository.createGame(game.value!!)
                }
                success.value = true
            }
        }
    }

    /**
     * Basic validation method
     */
    private fun isNoteValid(): Boolean {
        return when {
            game.value == null -> {
                error.value = "Game must not be null"
                false
            }
            game.value!!.title.isBlank() -> {
                error.value = "Title must not be empty"
                false
            }
            game.value!!.platform.isBlank() -> {
                error.value = "Platform must not be empty"
                false
            }
            game.value!!.releaseDate == null -> {
                error.value = "Make sure to input a correct release date!"
                false
            }
            else -> true
        }
    }

}