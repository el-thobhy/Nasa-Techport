package com.elthobhy.nasatechport.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.elthobhy.nasatechport.data.remote.TechportResponseItem

@Database(
    entities = [RemoteKeys::class, TechportResponseItem::class],
    version = 2,
    exportSchema = false
)
abstract class TechportDatabase : RoomDatabase() {

    abstract fun techportDao(): TechportDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: TechportDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): TechportDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    TechportDatabase::class.java, "quote_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}