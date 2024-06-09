package m1k.kotik.negocards

import android.os.Bundle
import android.util.Log
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import m1k.kotik.negocards.custom_views.windows.stylized_window.StaticStylizedWindow
import m1k.kotik.negocards.databinding.ActivityMainBinding
import m1k.kotik.negocards.fragments.pages.ScannerPageFragment
import m1k.kotik.negocards.fragments.utils_fragment.IOnBackPressedListener


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    private lateinit var bottomBar: StaticStylizedWindow
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.fragmentContainerView)
        //setupActionBarWithNavController(navController)
        setupSmoothBottomMenu()
    }

    override fun onBackPressed() {
        val fm: FragmentManager = supportFragmentManager
        var backPressedListener: IOnBackPressedListener? = null
            val fragment = navController.currentDestination
            if (fragment is IOnBackPressedListener) {
                backPressedListener = fragment as IOnBackPressedListener
            }

        if (backPressedListener != null) {
            backPressedListener.onBackPressed()
        } else {
            super.onBackPressed()
        }
    }

    private fun setupSmoothBottomMenu() {
        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.bottom_navigation_bar)
        val menu = popupMenu.menu

        //val menu = MenuBuilder(applicationContext)
        //menuInflater.inflate(R.menu.bottom_navigation_bar,menu)

        binding.bottomBar.setupWithNavController(menu, navController)
        binding.bottomBar.onItemReselected = {
            val currentIndex = binding.bottomBar.itemActiveIndex
            val currentMenuItem = menu.getItem(currentIndex)
            val currentId = currentMenuItem.itemId
            navController.navigate(currentId)
        }
        //binding.bottomBar.setupWithNavController(navController)
    }

}