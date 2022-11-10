package com.asep.storyapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.*
import com.asep.storyapp.data.StoryRemoteMediator
import com.asep.storyapp.data.datasource.local.database.StoryDatabase
import com.asep.storyapp.data.datasource.local.entity.StoryEntity
import com.asep.storyapp.data.datasource.remote.api.StoryService
import com.asep.storyapp.data.datasource.remote.response.GeneralResponse
import com.asep.storyapp.ui.domain.model.Story
import com.asep.storyapp.ui.domain.model.toDomain
import com.asep.storyapp.util.Result
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryRepository(
    private val storyDatabase: StoryDatabase, private val storyService: StoryService
) {
    fun getStories(token: String): LiveData<PagingData<StoryEntity>> {
        @OptIn(ExperimentalPagingApi::class) return Pager(config = PagingConfig(pageSize = 10),
            remoteMediator = StoryRemoteMediator(
                storyDatabase, storyService, generateBearerToken(token)
            ),
            pagingSourceFactory = {
                storyDatabase.storyDao().getStories()
            }).liveData
    }

    fun getAllStoriesWithLocation(token: String): LiveData<Result<List<Story>>> = liveData {
        emit(Result.Loading())
        try {
            val response = storyService.getStories(
                generateBearerToken(token), size = 10, location = 1
            )

            if (!response.error) {
                emit(Result.Success(response.listStory.map { storyDto ->
                    storyDto.toDomain()
                }))
            } else {
                emit(Result.Error(response.message))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }

    fun uploadStory(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody? = null,
        lon: RequestBody? = null
    ): LiveData<Result<GeneralResponse>> = liveData {
        emit(Result.Loading())
        try {
            val response = storyService.postStory(
                generateBearerToken(token), file, description, lat, lon
            )

            if (response.error == false) {
                emit(Result.Success(response))
            } else {
                emit(Result.Error(response.message))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }

    companion object {
        private var INSTANCE: StoryRepository? = null
        fun getInstance(storyService: StoryService, storyDatabase: StoryDatabase): StoryRepository {
            return INSTANCE ?: synchronized(this) {
                StoryRepository(storyDatabase, storyService).also {
                    INSTANCE = it
                }
            }
        }

        private fun generateBearerToken(token: String): String {
            return "Bearer $token"
        }
    }
}