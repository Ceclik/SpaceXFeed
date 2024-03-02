package com.yyi.spacexfeed.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yyi.spacexfeed.EditActivity
import com.yyi.spacexfeed.SpaceEventAdapter
import com.yyi.spacexfeed.dataClasses.SpaceEvent
import com.yyi.spacexfeed.dataClasses.SpaceEventsDataModel
import com.yyi.spacexfeed.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {

    private lateinit var bindingClass: FragmentNewsBinding

    private val adapter = SpaceEventAdapter()
    private var editLauncher: ActivityResultLauncher<Intent>? = null

    private val spaceEventsModel: SpaceEventsDataModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingClass = FragmentNewsBinding.inflate(inflater)
        editLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == AppCompatActivity.RESULT_OK)
                adapter.addSpaceEvent(it.data?.getSerializableExtra("spaceEvent") as SpaceEvent)
        }
        init()
        return bindingClass.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = NewsFragment()
    }

    override fun onPause() {
        super.onPause()
        spaceEventsModel.spaceEvents = adapter.spaceEvents
    }

    override fun onResume() {
        super.onResume()
        adapter.initSpaceEventsArray(spaceEventsModel.spaceEvents)
    }

    private fun init() = with(bindingClass){
        rcView.layoutManager = LinearLayoutManager(activity)
        rcView.adapter = adapter
        addEventButton.setOnClickListener{
            editLauncher?.launch(Intent(activity, EditActivity::class.java))
        }
    }
}