package com.yyi.spacexfeed

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yyi.spacexfeed.dataClasses.SpaceEvent
import com.yyi.spacexfeed.database.MainDB
import com.yyi.spacexfeed.databinding.SpaceEventItemBinding

class SpaceEventAdapter(var db: MainDB) :
    RecyclerView.Adapter<SpaceEventAdapter.SpaceEventHolder>() {

    var spaceEvents = ArrayList<SpaceEvent>()

    inner class SpaceEventHolder(item: View) : RecyclerView.ViewHolder(item) {
        private var binding = SpaceEventItemBinding.bind(item)

        @SuppressLint("NotifyDataSetChanged")
        fun bind(spaceEvent: SpaceEvent) = with(binding) {
            eventIcon.setImageResource(spaceEvent.iconId)
            titleText.text = spaceEvent.title
            eventDate.text = spaceEvent.date

            deleteItemButton.setOnClickListener {
                spaceEvents.remove(spaceEvent)
                Thread {
                    db.getDAO().deleteSpaceEvent(spaceEvent)
                }.start()
                notifyDataSetChanged()
            }

            if(spaceEvent.isFavourite)
                addToFavouritesButton.setImageResource(R.drawable.ic_liked_like)
            else
                addToFavouritesButton.setImageResource(R.drawable.ic_favourite)

            addToFavouritesButton.setOnClickListener {
                if(!spaceEvent.isFavourite) {
                    spaceEvent.isFavourite = true
                    Thread {
                        db.getDAO().updateSpaceEvent(spaceEvent)
                    }.start()
                    addToFavouritesButton.setImageResource(R.drawable.ic_liked_like)
                }
                else{
                    spaceEvent.isFavourite = false
                    Thread {
                        db.getDAO().updateSpaceEvent(spaceEvent)
                    }.start()
                    addToFavouritesButton.setImageResource(R.drawable.ic_favourite)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpaceEventHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.space_event_item, parent, false)
        return SpaceEventHolder(view)
    }

    override fun getItemCount(): Int {
        return spaceEvents.size
    }

    override fun onBindViewHolder(holder: SpaceEventHolder, position: Int) {
        holder.bind(spaceEvents[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun initSpaceEventsArray(spaceEvents: ArrayList<SpaceEvent>) {
        this.spaceEvents = spaceEvents
        notifyDataSetChanged()
    }

}