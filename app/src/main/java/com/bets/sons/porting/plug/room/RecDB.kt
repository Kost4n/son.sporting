package com.bets.sons.porting.plug.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [
        Rec::class
    ]
)
abstract class RecDB: RoomDatabase() {
    abstract fun getRecDao(): RecDao

    companion object {
        @Volatile
        private var INSTANCE: RecDB? = null

        fun getDatabase(context: Context): RecDB {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = buildDatabase(context)
                }
            }
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): RecDB {
            return Room.databaseBuilder(
                context.applicationContext,
                RecDB::class.java,
                "note_database"
            ).build()
        }
    }
}