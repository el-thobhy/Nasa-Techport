package com.elthobhy.nasatechport.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Apod(
    val date: String? = null,
    val copyright: String? = null,
    val mediaType: String? = null,
    val hdurl: String? = null,
    val serviceVersion: String? = null,
    val explanation: String? = null,
    val title: String? = null,
    val url: String? = null
): Parcelable