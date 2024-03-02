package com.yyi.spacexfeed

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yyi.spacexfeed.dataClasses.SpaceEvent
import com.yyi.spacexfeed.databinding.SpaceEventItemBinding

class SpaceEventAdapter: RecyclerView.Adapter<SpaceEventAdapter.SpaceEventHolder>() {

    var spaceEvents = ArrayList<SpaceEvent>()

    inner class SpaceEventHolder(item: View): RecyclerView.ViewHolder(item) {
        private var binding = SpaceEventItemBinding.bind(item)

        @SuppressLint("NotifyDataSetChanged")
        fun bind(spaceEvent: SpaceEvent) = with(binding){
            eventIcon.setImageResource(spaceEvent.iconId)
            titleText.text = spaceEvent.title
            eventDate.text = spaceEvent.date

            deleteItemButton.setOnClickListener{
                spaceEvents.remove(spaceEvent)
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpaceEventHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.space_event_item, parent, false)
        return SpaceEventHolder(view)
    }

    override fun getItemCount(): Int {
        return spaceEvents.size
    }

    override fun onBindViewHolder(holder: SpaceEventHolder, position: Int) {
        holder.bind(spaceEvents[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addSpaceEvent(spaceEvent: SpaceEvent){
        spaceEvents.add(spaceEvent)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun initSpaceEventsArray(spaceEvents: ArrayList<SpaceEvent>){
        this.spaceEvents = spaceEvents
        notifyDataSetChanged()
    }

}