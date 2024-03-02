package com.yyi.spacexfeed.dataClasses

import java.io.Serializable

data class SpaceEvent(var title: String, var description: String, var date: String, var iconId: Int): Serializable{

    constructor(): this("", "", "", 0)
}
