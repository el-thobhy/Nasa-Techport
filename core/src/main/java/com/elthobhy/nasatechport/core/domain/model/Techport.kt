package com.elthobhy.nasatechport.core.domain.model

import android.os.Parcelable
import com.elthobhy.nasatechport.core.data.remote.response.Projectapiurl
import com.elthobhy.nasatechport.core.data.remote.response.Projecturl
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Techport(
    val primarytaxonomy: String? = null,
    val projecturl: @RawValue Projecturl? = null,
    val projectapiurl: @RawValue Projectapiurl? = null,
    val description: String? = null,
    val lastupdated: String? = null,
    val title: String? = null,
    val projectid: String,
    val responsiblenasaprogram: String? = null,
    var isFavorite: Boolean = false
): Parcelable
