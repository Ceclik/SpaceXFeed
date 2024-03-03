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
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.yyi.spacexfeed.EditActivity
import com.yyi.spacexfeed.SpaceEventAdapter
import com.yyi.spacexfeed.dataClasses.SpaceEvent
import com.yyi.spacexfeed.database.MainDB
import com.yyi.spacexfeed.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {

    private lateinit var bindingClass: FragmentNewsBinding

    private lateinit var adapter: SpaceEventAdapter

    private var editLauncher: ActivityResultLauncher<Intent>? = null

    private lateinit var db: MainDB

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("myLog", "in on attach")
        db = MainDB.getDb(context)
        adapter = SpaceEventAdapter(db)

        db.getDAO().getAllItems().asLiveData().observe(requireActivity()) {
            val existingSpaceEvents = ArrayList<SpaceEvent>()
            for (event in it)
                existingSpaceEvents.add(event)
            adapter.initSpaceEventsArray(existingSpaceEvents)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("myLog", "in on create view")
        bindingClass = FragmentNewsBinding.inflate(inflater)
        return bindingClass.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("myLog", "in on view created")
        super.onViewCreated(view, savedInstanceState)
        editLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == AppCompatActivity.RESULT_OK) {
                //adapter.addSpaceEvent(it.data?.getSerializableExtra("spaceEvent") as SpaceEvent)
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