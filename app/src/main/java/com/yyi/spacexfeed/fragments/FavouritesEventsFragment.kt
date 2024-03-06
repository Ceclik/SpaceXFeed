package com.yyi.spacexfeed.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.yyi.spacexfeed.EditActivity
import com.yyi.spacexfeed.R
import com.yyi.spacexfeed.SpaceEventAdapter
import com.yyi.spacexfeed.dataClasses.SpaceEvent
import com.yyi.spacexfeed.database.MainDB
import com.yyi.spacexfeed.databinding.FragmentFavouritesEventsBinding

class FavouritesEventsFragment : Fragment() {
    private lateinit var binding: FragmentFavouritesEventsBinding
    private lateinit var adapter: SpaceEventAdapter
    private lateinit var db: MainDB

    override fun onAttach(context: Context) {
        super.onAttach(context)
        db = MainDB.getDb(context)
        adapter = SpaceEventAdapter(db)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouritesEventsBinding.inflate(inflater)
        init()
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavouritesEventsFragment()
    }

    private fun init() = with(binding) {
        rcView.layoutManager = LinearLayoutManager(activity)
        rcView.adapter = adapter
        initFavouritesEvents()
    }

    private fun initFavouritesEvents(){
        db.getDAO().getAllFavourites().asLiveData().observe(requireActivity()) {
            val existingSpaceEvents = ArrayList<SpaceEvent>()
            for (event in it)
                existingSpaceEvents.add(event)
            adapter.initSpaceEventsArray(existingSpaceEvents)
        }
    }
}