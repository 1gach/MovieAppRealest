package com.example.moviesapp2.UI.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.moviesapp2.R
import com.example.moviesapp2.UI.ViewModel.MainViewModel
import com.example.moviesapp2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
//    private val posters = listOf(
//        R.drawable.movie_podter2,
//        R.drawable.movie_podter2
//    )
//    private val numbers = listOf(R.drawable.number_1,R.drawable.number_2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        NavigationUI.setupWithNavController(binding.bottomNav, navController)

        val mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]



        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> binding.bottomNav.menu.findItem(R.id.homeFragment).isChecked = true
                R.id.searchFragment -> binding.bottomNav.menu.findItem(R.id.searchFragment).isChecked = true
                R.id.watchListFragment -> binding.bottomNav.menu.findItem(R.id.watchListFragment).isChecked = true
                R.id.detailFragment -> binding.bottomNav.menu.findItem(R.id.homeFragment).isChecked = true
                // Don't check anything for DetailFragment, etc.
                else -> binding.bottomNav.menu.setGroupCheckable(0, false, true)
            }
        }


        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.searchFragment -> {
                    // Navigate to HomeFragment
                    navController.navigate(
                        R.id.searchFragment,
                        Bundle().apply { putBoolean("showSearch", true) })
                    // Signal HomeFragment to show the RecyclerView
                    mainViewModel.showSearchResults.value = true
                    true
                }
                R.id.homeFragment -> {
                // Navigate home, but make sure search view is closed
                navController.navigate(
                    R.id.homeFragment,
                    Bundle().apply { putBoolean("showSearch", false) }
                )
                true
            }
                R.id.watchListFragment -> {
                    // Navigate home, but make sure search view is closed
                    navController.navigate(
                        R.id.watchListFragment,
                        Bundle().apply { putBoolean("showSearch", false) }
                    )
                    true
                }
                else -> {
                    NavigationUI.onNavDestinationSelected(item, navController)
                    true
                }
            }
        }

    }


}