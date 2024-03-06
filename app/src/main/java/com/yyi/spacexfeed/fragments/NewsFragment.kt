package com.yyi.spacexfeed.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.yyi.spacexfeed.EditActivity
import com.yyi.spacexfeed.SpaceEventAdapter
import com.yyi.spacexfeed.dataClasses.SpaceEvent
import com.yyi.spacexfeed.database.MainDB
import com.yyi.spacexfeed.databinding.FragmentNewsBinding
import com.yyi.spacexfeed.viewModels.SpaceEventsViewModel

class NewsFragment : Fragment() {

    private lateinit var bindingClass: FragmentNewsBinding

    private lateinit var adapter: SpaceEventAdapter

    private var editLauncher: ActivityResultLauncher<Intent>? = null

    private lateinit var db: MainDB

    private lateinit var viewModel: SpaceEventsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        db = MainDB.getDb(context)
        adapter = SpaceEventAdapter(db)
    }

    private fun initSpaceEvents(substring: String?) {
        if (substring == null) {
            db.getDAO().getAllItems().asLiveData().observe(requireActivity()) {
                val existingSpaceEvents = ArrayList<SpaceEvent>()
                for (event in it)
                    existingSpaceEvents.add(event)
                adapter.initSpaceEventsArray(existingSpaceEvents)
            }
        } else {
            db.getDAO().getAllWithSubString(substring).asLiveData().observe(requireActivity()) {
                val existingSpaceEvents = ArrayList<SpaceEvent>()
                for (event in it)
                    existingSpaceEvents.add(event)
                adapter.initSpaceEventsArray(existingSpaceEvents)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingClass = FragmentNewsBinding.inflate(inflater)
        viewModel = ViewModelProvider(requireActivity())[SpaceEventsViewModel::class.java]
        initSpaceEvents(null)
        viewModel.data.observe(viewLifecycleOwner){newValue -> initSpaceEvents(newValue)}
        return bindingClass.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == AppCompatActivity.RESULT_OK) {
                Thread {
                    db.getDAO()
                        .insertSpaceEvent(it.data?.getSerializableExtra("spaceEvent") as SpaceEvent)
                }.start()
            }
        }
        init()
    }

    companion object {
        @JvmStatic
        fun newInstance() = NewsFragment()
    }

    private fun init() = with(bindingClass) {
        rcView.layoutManager = LinearLayoutManager(activity)
        rcView.adapter = adapter
        addEventButton.setOnClickListener {
            editLauncher?.launch(Intent(activity, EditActivity::class.java))
        }
    }
}