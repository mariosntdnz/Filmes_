package com.example.filmes_

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.SurfaceControl
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.filmes_.ui.filmes.FilmesFragmentDirections


class MainActivity : AppCompatActivity() {

    companion object{
        lateinit var instance : MainActivity
    }

    init {
        instance = this
    }

    lateinit var navControl: NavController
    lateinit var navGraph: NavGraph
    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        navControl = supportFragmentManager.fragments.find { it.id == R.id.nav_host_fragment }!!.findNavController()
        navGraph = navControl.graph
        appBarConfiguration = AppBarConfiguration(navGraph)
        setupActionBarWithNavController(navControl, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.filmes_favoritos -> {
                supportFragmentManager.fragments[0].findNavController().navigate(
                    FilmesFragmentDirections.actionFilmesFragmentToFavoritosFragment()
                )
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }

        }
    }
    override fun onSupportNavigateUp(): Boolean {
        return navControl.navigateUp()
                || super.onSupportNavigateUp()
    }
}

