package m1k.kotik.negocards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import androidx.navigation.NavController
import androidx.navigation.findNavController
import m1k.kotik.negocards.databinding.ActivityMainBinding

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