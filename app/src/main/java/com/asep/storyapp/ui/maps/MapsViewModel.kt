package com.asep.storyapp.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.asep.storyapp.data.repository.StoryRepository
import com.asep.storyapp.data.domain.model.Story
import com.asep.storyapp.util.Result

class MapsViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun fetchAllStoryWithLocation(token: String): LiveData<Result<List<Story>>> =
        storyRepository.getAllStoriesWithLocation(token)
}