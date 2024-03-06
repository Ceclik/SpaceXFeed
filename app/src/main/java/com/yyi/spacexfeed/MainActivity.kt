package com.yyi.spacexfeed

import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.yyi.spacexfeed.databinding.ActivityMainBinding
import com.yyi.spacexfeed.fragments.AboutAppFragment
import com.yyi.spacexfeed.fragments.FavouritesEventsFragment
import com.yyi.spacexfeed.fragments.NewsFragment
import com.yyi.spacexfeed.viewModels.SpaceEventsViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: SpaceEventsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel =
            ViewModelProvider(this).get(SpaceEventsViewModel::class.java)
        init()

    }

    private fun init() = with(binding) {
        val searchField = toolbar2.menu.findItem(R.id.app_bar_search)
        val searchView = searchField.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //processing text after on search icon click
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                viewModel.data.value = query
                return false
            }
        })

        bottomBar.selectedItemId = R.id.newsItem

        bottomBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.newsItem -> {
                    toolbar2.title = getString(R.string.feed)
                    searchField.setVisible(true)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.placeHolder, NewsFragment.newInstance()).commit()
                }

                R.id.favouritesItem -> {
                    toolbar2.title = getString(R.string.favourites)
                    searchField.setVisible(false)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.placeHolder, FavouritesEventsFragment.newInstance()).commit()
                }

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