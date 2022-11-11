package com.asep.storyapp.utils

import com.asep.storyapp.data.datasource.remote.dto.LoginResultDto
import com.asep.storyapp.data.datasource.remote.response.GeneralResponse
import com.asep.storyapp.data.datasource.remote.response.LoginResponse
import com.asep.storyapp.data.domain.model.Story
import com.asep.storyapp.data.domain.model.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

object DataDummy {
    fun generateDummyLoginResponse(): LoginResponse {
        val loginResultDto = LoginResultDto(
            name = "asep",
            userId = "user-0Cule-UOVPfC8qAV",
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLTBDdWxlLVVPVlBmQzhxQVYiLCJpYXQiOjE2NjgxNzU5OTB9.fVSTZnmZBLauSxiVuPvqgi9B_q0_HmFDBIs__QNRKw8"
        )

        return LoginResponse(
            loginResult = loginResultDto, error = false, message = "success"
        )
    }

    fun generateDummyGeneralResponse(): GeneralResponse {
        return GeneralResponse(error = false, message = "success")
    }

    fun generateDummyStoriesWithoutLocation(): List<Story> {
        val stories = arrayListOf<Story>()
        for (i in 1..10) {
            val story = Story(
                id = "story-l1d3xeoSawLT1btR",
                photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1668176446014_0hGAAXMT.png",
                createdAt = "2022-11-11T14:20:46.015Z",
                name = "Guest",
                description = "coba_abu hammad",
                lon = null,
                lat = null
            )
            stories.add(story)
        }
        return stories
    }

    fun generateDummyStoriesWithLocation(): List<Story> {
        val stories = arrayListOf<Story>()
        for (i in 1..10) {
            val story = Story(
                id = "story-l1d3xeoSawLT1btR",
                photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1668176446014_0hGAAXMT.png",
                createdAt = "2022-11-11T14:20:46.015Z",
                name = "Guest",
                description = "coba_abu hammad",
                lon = -6.3874567,
                lat = 106.9391836
            )
            stories.add(story)
        }
        return stories
    }

    fun generateDummyMultipartFile(): MultipartBody.Part {
        val dummyText = "text"
        return MultipartBody.Part.create(dummyText.toRequestBody())
    }

    fun generateDummyRequestBody(): RequestBody {
        val dummyText = "text"
        return dummyText.toRequestBody()
    }

    fun generateDummyLoggedInUser(): User {
        return User(
            name = "asep",
            userId = "user-0Cule-UOVPfC8qAV",
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLTBDdWxlLVVPVlBmQzhxQVYiLCJpYXQiOjE2NjgxNzU5OTB9.fVSTZnmZBLauSxiVuPvqgi9B_q0_HmFDBIs__QNRKw8",
            isLogin = true
        )
    }

    fun generateDummyLoggedOutUser(): User {
        return User(
            "", "", "", false
        )
    }
}