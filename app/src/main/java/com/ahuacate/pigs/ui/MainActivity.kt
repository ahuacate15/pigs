package com.ahuacate.pigs.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.ahuacate.pigs.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appBarConfiguration : AppBarConfiguration = AppBarConfiguration.Builder(R.id.nav_home)
            .build()

        val navController : NavController = Navigation.findNavController(this, R.id.fMainActivity)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

        /* navMainActivity is referenced automated by synthetic package */
        NavigationUI.setupWithNavController(navMainActivity, navController)
    }
}