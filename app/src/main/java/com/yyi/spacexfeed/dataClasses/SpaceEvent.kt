package com.yyi.spacexfeed.dataClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity (tableName = "spaceEvents")
data class SpaceEvent(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "date")
    var date: String,
    @ColumnInfo(name = "iconId")
    var iconId: Int,
    @ColumnInfo(name = "isFavourite")
    var isFavourite: Boolean
        ): Serializable{

    constructor(): this(null,"", "", "", 0, false)
    constructor(title: String, description: String, date: String, iconId: Int = 0, isFavourite: Boolean = false):
            this(null, title, description, date, iconId, isFavourite)

}
