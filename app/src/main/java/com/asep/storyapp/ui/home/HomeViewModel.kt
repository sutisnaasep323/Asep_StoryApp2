package com.asep.storyapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.asep.storyapp.data.repository.StoryRepository
import com.asep.storyapp.data.domain.model.Story
import com.asep.storyapp.data.domain.model.toDomain

class HomeViewModel(
    private val storyRepository: StoryRepository
) : ViewModel() {
    fun fetchStories(token: String): LiveData<PagingData<Story>> =
        storyRepository.getStories(token).map { result ->
            result.map { storyEntity ->
                storyEntity.toDomain()
            }
        }.cachedIn(viewModelScope)
}