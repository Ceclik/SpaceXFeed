package com.yyi.spacexfeed.dataClasses

import androidx.lifecycle.ViewModel

open class SpaceEventsDataModel: ViewModel() {
    var spaceEvents = ArrayList<SpaceEvent>()
}