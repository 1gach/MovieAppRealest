package com.example.moviesapp2

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp2.Search.MainViewModel
import com.example.moviesapp2.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

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
            .findFragmentById(R.id.nav_host_fragment) as androidx.navigation.fragment.NavHostFragment
        val navController = navHostFragment.navController

        val mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]


        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.searchFragment -> {
                    // Navigate to HomeFragment
                    navController.navigate(R.id.homeFragment,
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
                else -> {
                    NavigationUI.onNavDestinationSelected(item, navController)
                    true
                }
            }
        }

    }

//     fun loadFragment(f:Int){
//        findNavController().navigate(f)
//    }


//    private fun botNav(){
//        binding.bottomNav.setOnItemSelectedListener {
//            when(it.itemId){
//                R.id.home_icon -> {
//                    loadFragment(R.id.homeFragment)
//                    true
//                }
//                R.id.search -> {
//                    loadFragment(R.id.searchFragment)
//                    true
//                }
//                R.id.watchList -> {
//                    loadFragment(R.id.watchListFragment)
//                    true
//                }
//                else -> {
//                    loadFragment(R.id.homeFragment)
//                    true
//                }
//            }
//
//        }
//    }

//    private fun addPosters(){
//        binding.recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        binding.recycler.adapter = ImageAdapter(posters, numbers)
//
//
//
//    }

}