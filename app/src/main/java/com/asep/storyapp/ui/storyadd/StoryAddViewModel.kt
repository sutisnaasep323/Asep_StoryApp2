package com.asep.storyapp.ui.storyadd

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.asep.storyapp.data.datasource.remote.response.GeneralResponse
import com.asep.storyapp.data.repository.StoryRepository
import com.asep.storyapp.util.Result
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryAddViewModel(
    private val storyRepository: StoryRepository
) : ViewModel() {
    fun uploadImage(
        token: String,
        imageMultipart: MultipartBody.Part,
        description: RequestBody,
        latitude: RequestBody?,
        longitude: RequestBody?
    ): LiveData<Result<GeneralResponse>> =
        storyRepository.uploadStory(token, imageMultipart, description, latitude, longitude)
}