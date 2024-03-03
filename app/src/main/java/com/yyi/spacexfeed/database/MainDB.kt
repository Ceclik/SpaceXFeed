package com.yyi.spacexfeed.database

import android.content.Context
import androidx.core.content.ContextCompat.getString
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.yyi.spacexfeed.R
import com.yyi.spacexfeed.dataClasses.SpaceEvent


@Database(entities = [SpaceEvent::class], version = 2)
abstract class MainDB : RoomDatabase() {

    abstract fun getDAO(): DAO

    companion object {
        fun getDb(context: Context): MainDB {
            return Room.databaseBuilder(
                context.applicationContext,
                MainDB::class.java,
                getString(context, R.string.database_name)
            ).addMigrations(MIGRATION_1_2).build()
        }

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE spaceEvents ADD COLUMN isFavourite INTEGER DEFAULT 0 NOT NULL")
            }
        }
    }


}