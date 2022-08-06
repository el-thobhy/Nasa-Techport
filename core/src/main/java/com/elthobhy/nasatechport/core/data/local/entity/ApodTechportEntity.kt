package com.elthobhy.nasatechport.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "apod_techport")
data class ApodTechportEntity(
    @PrimaryKey
    @ColumnInfo(name = "title_search")
    var title_search: String,

    @ColumnInfo(name = "name_search")
    var name_search: String? = null,

    @ColumnInfo(name = "date_search")
    var date_search: String? = null,

    @ColumnInfo(name = "image_search")
    var image_search: String? = null,

    @ColumnInfo(name = "project_search")
    var projectId: String? = null,
)
