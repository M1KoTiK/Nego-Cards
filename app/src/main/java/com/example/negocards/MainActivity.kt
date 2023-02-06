package com.example.negocards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.negocards.databinding.ActivityMainBinding
import me.ibrahimsn.lib.SmoothBottomBar

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
       // navController = findNavController(R.id.)
//        setupActionBarWithNavController(navController)
        setupSmoothBottomMenu()
        setContentView(R.layout.activity_main)
    }
    private fun setupSmoothBottomMenu() {
        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.bottom_navigation_bar)
        val menu = popupMenu.menu
        //binding.bottomBar.setupWithNavController(menu, navController)
       // binding.bottomBar.setupWithNavController( navController)
    }
}