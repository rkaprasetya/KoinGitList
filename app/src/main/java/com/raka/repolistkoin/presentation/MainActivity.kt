package com.raka.repolistkoin.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.raka.repolistkoin.R

/**
 * Koin tutorial
 * https://medium.com/@sreeharikv112/dependency-injection-using-koin-4de4a3494cbe
 */
class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavBar :BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavBar = findViewById(R.id.btm_nav_view)
        NavigationUI.setupWithNavController(
            bottomNavBar,
            findNavController(R.id.main_nav_fragment)
        )
    }
    override fun onSupportNavigateUp() = findNavController(R.id.main_nav_fragment).navigateUp()
}
