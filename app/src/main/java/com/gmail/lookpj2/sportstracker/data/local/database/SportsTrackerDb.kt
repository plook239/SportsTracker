package com.gmail.lookpj2.sportstracker.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gmail.lookpj2.sportstracker.data.local.dao.TeamDao
import com.gmail.lookpj2.sportstracker.data.local.entities.TeamEntity

@Database(entities = [TeamEntity::class], version = 1, exportSchema = false)
abstract class SportsTrackerDb : RoomDatabase(){

    companion object {
        private var db: SportsTrackerDb? = null

        fun getInstance(context: Context): SportsTrackerDb {
            if (db == null) {
                synchronized(SportsTrackerDb::class) {
                    if (db == null) {
                        db = Room.databaseBuilder(context.applicationContext,
                                SportsTrackerDb::class.java, "sportstracker_db")
                                .allowMainThreadQueries()
                                .build()
                    }
                }
            }
            return db!!
        }
    }

    abstract fun getTeamDao(): TeamDao
}