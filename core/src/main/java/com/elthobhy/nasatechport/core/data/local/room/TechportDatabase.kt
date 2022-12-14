package com.elthobhy.nasatechport.core.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.elthobhy.nasatechport.core.data.local.entity.ApodEntity
import com.elthobhy.nasatechport.core.data.local.entity.ApodTechportEntity
import com.elthobhy.nasatechport.core.data.local.entity.TechportEntity

@Database(
    entities = [
        RemoteKeys::class,
        TechportEntity::class,
        ApodEntity::class,
        ApodTechportEntity::class],
    version = 2,
    exportSchema = false
)
abstract class TechportDatabase : RoomDatabase() {

    abstract fun techportDao(): TechportDao
    abstract fun remoteKeysDao(): RemoteKeysDao

}