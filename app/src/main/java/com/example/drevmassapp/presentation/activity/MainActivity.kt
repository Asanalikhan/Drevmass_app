package com.example.drevmassapp.presentation.activity

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.drevmassapp.R
import com.example.drevmassapp.databinding.ActivityMainBinding
import com.example.drevmassapp.domain.repository.NavigationHostProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationHostProvider {

    private lateinit var binding: ActivityMainBinding
    private val splashScreenDuration = 1000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        //setTheme(R.style.SplashTheme)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomMenu.itemIconTintList = null
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomMenu.setupWithNavController(navController)

//        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
//        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)
//
//        if (isDarkMode) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//        } else {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//        }

    }

    override fun setNavigationVisibility(visibility: Boolean) {
        if(visibility){
            binding.bottomMenu.visibility = View.VISIBLE
        }else {
            binding.bottomMenu.visibility = View.GONE
        }
    }
}