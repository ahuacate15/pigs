package com.ahuacate.pigs.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.ahuacate.pigs.R
import com.ahuacate.pigs.ui.saving.SavingFormFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var bNewSaving : FloatingActionButton
    val TAG : String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appBarConfiguration : AppBarConfiguration = AppBarConfiguration.Builder(R.id.nav_home)
            .build()

        val navController : NavController = Navigation.findNavController(this, R.id.fMainActivity)
        bNewSaving = findViewById(R.id.bNewSaving)

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

        /* navMainActivity is referenced automated by synthetic package */
        NavigationUI.setupWithNavController(navMainActivity, navController)

        bNewSaving.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.bNewSaving -> {
                Log.d(TAG, "abriendo formulario")
                var savingFormFragment : SavingFormFragment = SavingFormFragment()
                savingFormFragment.show(supportFragmentManager, "new")
            }
        }
    }
}