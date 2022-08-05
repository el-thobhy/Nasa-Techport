package com.elthobhy.nasatechport.core.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class ApodResponseItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("copyright")
	val copyright: String? = null,

	@field:SerializedName("media_type")
	val mediaType: String? = null,

	@field:SerializedName("hdurl")
	val hdurl: String? = null,

	@field:SerializedName("service_version")
	val serviceVersion: String? = null,

	@field:SerializedName("explanation")
	val explanation: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("url")
	val url: String? = null
) : Parcelable
