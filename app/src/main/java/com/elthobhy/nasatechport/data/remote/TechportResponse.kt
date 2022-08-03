package com.elthobhy.nasatechport.data.remote

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

data class Projecturl(

	@field:SerializedName("description")
	val desc: String? = null,

	@field:SerializedName("url")
	val urlString: String? = null
)

@Entity(tableName = "tech_port")
data class TechportResponseItem(

	@field:SerializedName("primarytaxonomy")
	val primarytaxonomy: String? = null,

	@Embedded
	@field:SerializedName("projecturl")
	val projecturl: Projecturl? = null,

	@Embedded
	@field:SerializedName("projectapiurl")
	val projectapiurl: Projectapiurl? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("lastupdated")
	val lastupdated: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@PrimaryKey
	@field:SerializedName("projectid")
	val projectid: String,

	@field:SerializedName("responsiblenasaprogram")
	val responsiblenasaprogram: String? = null
)

data class Projectapiurl(

	@field:SerializedName("url")
	val url: String? = null
)
