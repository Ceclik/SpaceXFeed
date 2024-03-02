package com.yyi.spacexfeed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
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

    private fun init() = with(binding){
        val searchField = toolbar2.menu.findItem(R.id.app_bar_search)

        supportFragmentManager.beginTransaction().replace(R.id.placeHolder, NewsFragment()).commit()
        toolbar2.title = getString(R.string.feed)

        bottomBar.selectedItemId = R.id.newsItem
        bottomBar.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.newsItem -> {
                    toolbar2.title = getString(R.string.feed)
                    searchField.setVisible(true)
                    supportFragmentManager.beginTransaction().replace(R.id.placeHolder, NewsFragment()).commit()
                }

                R.id.favouritesItem -> Toast.makeText(this@MainActivity, "Favourites item selected!", Toast.LENGTH_SHORT).show()

                R.id.infoItem -> {
                    toolbar2.title = getString(R.string.app_name)
                    searchField.setVisible(false)
                    supportFragmentManager.beginTransaction().replace(R.id.placeHolder, AboutAppFragment()).commit()
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