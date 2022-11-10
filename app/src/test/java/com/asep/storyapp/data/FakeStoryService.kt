package com.asep.storyapp.data

import com.asep.storyapp.data.datasource.remote.api.StoryService
import com.asep.storyapp.data.datasource.remote.response.GeneralResponse
import com.asep.storyapp.data.datasource.remote.response.LoginResponse
import com.asep.storyapp.data.datasource.remote.response.StoriesResponse
import com.asep.storyapp.utils.DataDummy.generateDummyEmptyStoriesResponse
import com.asep.storyapp.utils.DataDummy.generateDummyLoginResponses
import com.asep.storyapp.utils.DataDummy.generateDummyPostStoryResponse
import com.asep.storyapp.utils.DataDummy.generateDummyRegisterResponse
import com.asep.storyapp.utils.DataDummy.generateDummyStoriesResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

/**
Written by Yayan Rahmat Wijaya on 10/22/2022 11:40
Github : https://github.com/yayanrw
 **/

class FakeStoryService : StoryService {
    override suspend fun postRegister(
        name: String, email: String, password: String
    ): GeneralResponse {
        return if (email == "yayanraw@gmail.com") {
            generateDummyRegisterResponse(true)
        } else {
            generateDummyRegisterResponse(false)
        }
    }

    override suspend fun postLogin(email: String, password: String): LoginResponse {
        return if (email == "yayanraw@gmail.com") {
            generateDummyLoginResponses(true)
        } else {
            generateDummyLoginResponses(false)
        }
    }

    override suspend fun postStory(
        auth: String,
        photo: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody?,
        lon: RequestBody?
    ): GeneralResponse {
        return generateDummyPostStoryResponse()
    }

    override suspend fun getStories(
        auth: String, page: Int?, size: Int?, location: Int?
    ): StoriesResponse {
        return if (auth == "Bearer token") {
            generateDummyStoriesResponse()
        } else {
            generateDummyEmptyStoriesResponse()
        }
    }
}