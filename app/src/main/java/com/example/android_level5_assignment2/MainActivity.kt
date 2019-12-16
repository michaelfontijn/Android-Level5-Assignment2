package com.example.android_level5_assignment2

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_level5_assignment2.Adapters.GameAdapter
import com.example.android_level5_assignment2.Database.GameRepository
import com.example.android_level5_assignment2.Models.Game
import com.example.android_level5_assignment2.ViewModels.CreateActivityViewModel
import com.example.android_level5_assignment2.ViewModels.MainActivityViewModel

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private val games = arrayListOf<Game>()
    private val gameAdapter = GameAdapter(games)

    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initView()
        initViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initView(){
        fab.setOnClickListener {
            val intent = Intent(this, CreateActivity::class.java)
            startActivity(intent)
        }

        initRecyclerView()
    }

    fun initViewModel(){
        //initialize the viewModel with an empty game object
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        //bind all the observers
        mainActivityViewModel.error.observe(this, Observer { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })
        mainActivityViewModel.message.observe(this, Observer { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })
        mainActivityViewModel.games.observe(this, Observer { games ->
            this.games.clear()
            this.games.addAll(games.toCollection(ArrayList()))
            gameAdapter.notifyDataSetChanged()
        })

        mainActivityViewModel.getGames()
    }

    /**
     * Initializes the recyclerView
     */
    private fun initRecyclerView(){
        //Bind the adapter and layout manger to the recyclerView.
        rvGames.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        rvGames.adapter = gameAdapter

        //attach the item touch helper to the recycler view (for swiping support)
        createItemTouchHelper().attachToRecyclerView(rvGames)
    }

    /***
     * this method is used to bind to the onSwipe and onMove method to the recycler view.
     */
    private fun createItemTouchHelper(): ItemTouchHelper {

        //create / configure the callback method on swipe left
        val callback = object :  ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){

            //on move just return false since we don't need a implementation of this method for this app
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            //The on swipe callback
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                //remove the game
                var game = games.elementAt(position)

                //remove the game and refresh the data source
                mainActivityViewModel.deleteGame(game)
                mainActivityViewModel.getGames()
            }
        }
        return ItemTouchHelper(callback)
    }
}
