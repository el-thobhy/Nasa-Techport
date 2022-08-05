package com.elthobhy.nasatechport.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "apod_entity")
data class ApodEntity(

    @ColumnInfo(name ="date")
    val date: String? = null,

    @ColumnInfo(name ="copyright")
    val copyright: String? = null,

    @ColumnInfo(name ="media_type")
    val mediaType: String? = null,

    @ColumnInfo(name ="hdurl")
    val hdurl: String? = null,

    @ColumnInfo(name ="service_version")
    val serviceVersion: String? = null,

    @ColumnInfo(name ="explanation")
    val explanation: String? = null,

    @ColumnInfo(name ="title")
    val title: String? = null,

    @PrimaryKey
    @ColumnInfo(name ="url")
    val url: String
)