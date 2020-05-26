package com.wearetriple.tripleonboarding.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.wearetriple.tripleonboarding.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar.title = getString(R.string.app_name)

        initNavigation()
    }

    /**
     * Initializes the bottom navigation and shows it only for specific fragments.
     */
    private fun initNavigation() {
        navController = findNavController(R.id.navHostFragment)

        NavigationUI.setupWithNavController(bnView, navController)

        val appBarConfiguration = AppBarConfiguration(bnView.menu)
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }
}