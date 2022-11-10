package com.asep.storyapp.ui.domain.model

import android.os.Parcelable
import com.asep.storyapp.data.datasource.local.entity.StoryEntity
import com.asep.storyapp.data.datasource.remote.dto.StoryDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class Story(
    val photoUrl: String,
    val createdAt: String,
    val name: String,
    val description: String,
    val lon: Double?,
    val id: String,
    val lat: Double?
) : Parcelable

fun StoryDto.toEntity(): StoryEntity {
    return StoryEntity(
        photoUrl = photoUrl,
        createdAt = createdAt,
        name = name,
        description = description,
        lon = lon,
        id = id,
        lat = lat
    )
}

fun StoryDto.toDomain(): Story {
    return Story(
        photoUrl = photoUrl,
        createdAt = createdAt,
        name = name,
        description = description,
        lon = lon,
        id = id,
        lat = lat
    )
}

fun StoryEntity.toDomain(): Story {
    return Story(
        photoUrl = photoUrl,
        createdAt = createdAt,
        name = name,
        description = description,
        lon = lon,
        id = id,
        lat = lat
    )
}

fun Story.toStoryEntity(): StoryEntity {
    return StoryEntity(
        id = id,
        name = name,
        description = description,
        createdAt = createdAt,
        photoUrl = photoUrl,
        lon = lon,
        lat = lat
    )
}