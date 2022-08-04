package com.elthobhy.nasatechport.core.utils

import androidx.paging.PagingData
import androidx.paging.map
import com.elthobhy.nasatechport.core.data.local.entity.TechportEntity
import com.elthobhy.nasatechport.core.data.remote.response.TechportResponseItem
import com.elthobhy.nasatechport.core.domain.model.Techport

object DataMapper {
    fun mapResponsesToEntities(input: List<TechportResponseItem>): List<TechportEntity> {
        val tourismList = ArrayList<TechportEntity>()
        input.map {
            val tourism = TechportEntity(
                projectid = it.projectid,
                description = it.description,
                title = it.title,
                primarytaxonomy = it.primarytaxonomy,
                lastupdated = it.lastupdated,
                projectapiurl = it.projectapiurl,
                projecturl = it.projecturl,
                responsiblenasaprogram = it.responsiblenasaprogram,
                isFavorite = false
            )
            tourismList.add(tourism)
        }
        return tourismList
    }

    fun mapPagingEntitiesToDomain(input: PagingData<TechportEntity>): PagingData<Techport> =
        input.map {
            Techport(
                projectid = it.projectid,
                description = it.description,
                title = it.title,
                responsiblenasaprogram = it.responsiblenasaprogram,
                lastupdated = it.lastupdated,
                projecturl = it.projecturl,
                projectapiurl = it.projectapiurl,
                primarytaxonomy = it.primarytaxonomy,
                isFavorite = it.isFavorite
            )
        }

    fun mapEntitiesToDomain(input: TechportEntity) =
            Techport(
                projectid = input.projectid,
                description = input.description,
                title = input.title,
                responsiblenasaprogram = input.responsiblenasaprogram,
                lastupdated = input.lastupdated,
                projecturl = input.projecturl,
                projectapiurl = input.projectapiurl,
                primarytaxonomy = input.primarytaxonomy,
                isFavorite = input.isFavorite)

    fun mapDomainToEntity(input: Techport) = TechportEntity(
        projectid = input.projectid,
        description = input.description,
        title = input.title,
        primarytaxonomy = input.primarytaxonomy,
        projectapiurl = input.projectapiurl,
        projecturl = input.projecturl,
        lastupdated = input.lastupdated,
        responsiblenasaprogram = input.responsiblenasaprogram,
        isFavorite = input.isFavorite
    )

}