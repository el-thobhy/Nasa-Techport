package com.elthobhy.nasatechport.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.elthobhy.nasatechport.core.data.remote.response.Projectapiurl
import com.elthobhy.nasatechport.core.data.remote.response.Projecturl


@Entity(tableName = "tech_port")
data class TechportEntity(

    @ColumnInfo(name ="primarytaxonomy")
    val primarytaxonomy: String? = null,

    @Embedded
    val projecturl: Projecturl? = null,

    @Embedded
    val projectapiurl: Projectapiurl? = null,

    @ColumnInfo(name ="description")
    val description: String? = null,

    @ColumnInfo(name ="lastupdated")
    val lastupdated: String? = null,

    @ColumnInfo(name ="title")
    val title: String? = null,

    @PrimaryKey
    @ColumnInfo(name ="projectid")
    val projectid: String,

    @ColumnInfo(name ="responsiblenasaprogram")
    val responsiblenasaprogram: String? = null,

    @ColumnInfo(name= "isFavorite")
    var isFavorite: Boolean = false
)
