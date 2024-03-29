package com.yyi.spacexfeed.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.yyi.spacexfeed.dataClasses.SpaceEvent
import kotlinx.coroutines.flow.Flow

@Dao
interface DAO {

    @Insert
    fun insertSpaceEvent(spaceEvent: SpaceEvent)

    @Query("SELECT * FROM spaceEvents")
    fun getAllItems(): Flow<List<SpaceEvent>>

    @Delete
    fun deleteSpaceEvent(spaceEvent: SpaceEvent)

    @Query("SELECT * FROM spaceEvents WHERE title LIKE :substring || '%'")
    fun getAllWithSubString(substring: String): Flow<List<SpaceEvent>>

    @Update
    fun updateSpaceEvent(spaceEvent: SpaceEvent)

    @Query("SELECT * FROM spaceEvents WHERE isFavourite IS 1")
    fun getAllFavourites(): Flow<List<SpaceEvent>>
}