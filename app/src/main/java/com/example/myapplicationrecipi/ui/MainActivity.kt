package com.example.myapplicationrecipi.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.myapplicationrecipi.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController:NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)  as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfigure = AppBarConfiguration(setOf(
            R.id.recepiFragment,
            R.id.favoriteRecepiFragment,
            R.id.foodJokeFragment
        )
        )
        bottomNavigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController,appBarConfigure)


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}