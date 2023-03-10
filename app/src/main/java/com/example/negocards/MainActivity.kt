package com.example.negocards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.view.menu.MenuBuilder
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.negocards.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.fragmentContainerView)
        //setupActionBarWithNavController(navController)
        setupSmoothBottomMenu()
    }
    private fun setupSmoothBottomMenu() {
        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.bottom_navigation_bar)
        val menu = popupMenu.menu
        //val menu = MenuBuilder(applicationContext)
        //menuInflater.inflate(R.menu.bottom_navigation_bar,menu)

        binding.bottomBar.setupWithNavController(menu,navController)
        binding.bottomBar.onItemReselected = {
            val currentIndex = binding?.bottomBar.itemActiveIndex
            val currentMenuItem = menu.getItem(currentIndex)
            val currentId = currentMenuItem.itemId
            navController.navigate(currentId)
        }
        //binding.bottomBar.setupWithNavController(navController)
    }   

}