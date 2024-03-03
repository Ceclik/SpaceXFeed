package com.yyi.spacexfeed

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yyi.spacexfeed.databinding.ActivityMainBinding
import com.yyi.spacexfeed.fragments.AboutAppFragment
import com.yyi.spacexfeed.fragments.NewsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

    }

    private fun init() = with(binding) {
        val searchField = toolbar2.menu.findItem(R.id.app_bar_search)

        bottomBar.selectedItemId = R.id.newsItem
        searchField.setOnMenuItemClickListener {
            Log.d("myLog", "Search Field clicked")
            true
        }

        bottomBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.newsItem -> {
                    toolbar2.title = getString(R.string.feed)
                    searchField.setVisible(true)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.placeHolder, NewsFragment.newInstance()).commit()
                }

                R.id.favouritesItem -> Toast.makeText(
                    this@MainActivity,
                    "Favourites item selected!",
                    Toast.LENGTH_SHORT
                ).show()

                R.id.infoItem -> {
                    toolbar2.title = getString(R.string.app_name)
                    searchField.setVisible(false)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.placeHolder, AboutAppFragment()).commit()
                }
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }
}