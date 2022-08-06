package com.elthobhy.nasatechport.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ApodTechportDomain(
    var title: String? = null,
    var name: String? = null,
    var date: String? = null,
    var projectId: String? = null,
    val image: String? = null
): Parcelable