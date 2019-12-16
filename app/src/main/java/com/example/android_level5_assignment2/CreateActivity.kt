package com.example.android_level5_assignment2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.android_level5_assignment2.Models.Game
import com.example.android_level5_assignment2.ViewModels.CreateActivityViewModel
import kotlinx.android.synthetic.main.activity_create.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.log

class CreateActivity : AppCompatActivity() {

    private lateinit var createActivityViewModel: CreateActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        initView()
        initViewModel()
    }

    private fun initView(){
        //Set the onclick listener on the floating action button
        fabSave.setOnClickListener{
            createGame()
        }
    }

    private fun initViewModel() {
        //initialize the viewModel with an empty game object
        createActivityViewModel = ViewModelProviders.of(this).get(CreateActivityViewModel::class.java)
        createActivityViewModel.game.value = Game("", "" , null)

        //observe the errorPorperty of the viewModel, in case it changes show the error to the user.
        createActivityViewModel.error.observe(this, Observer { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })

        //observe the successProperty of the viewModel, if it becomes true than finish, this closes the activity
        createActivityViewModel.success.observe(this, Observer { success ->
            if (success) finish()
        })
    }

    /***
     * Creates a new game based on the users input
     */
    private fun createGame(){
        //try to convert the date input to a date object
        var date : Date? = null
        var dateString = txtDayInput.text.toString() + "-" + txtMonthInput.text.toString() + "-" + txtYearInput.text.toString()

        if(dateString.isNotBlank()) try {
            date = Date(txtYearInput.text.toString().toInt(),txtMonthInput.text.toString().toInt(),txtDayInput.text.toString().toInt())
        }
         catch (ex : Exception){
             //doNothing
         }

        //set the value of the gameObject liveData property in the viewModel and as the viewModel to create the game based on the set data
        createActivityViewModel.game.value?.apply {
            title = txtTitleInput.text.toString()
            platform = txtPlatformInput.text.toString()
            releaseDate = date
        }
        createActivityViewModel.createGame()
    }
}
