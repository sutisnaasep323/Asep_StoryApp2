package com.asep.storyapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.asep.storyapp.data.datasource.local.database.StoryDatabase
import com.asep.storyapp.data.datasource.local.datastore.UserDataStore
import com.asep.storyapp.data.datasource.remote.api.StoryApi
import com.asep.storyapp.data.repository.StoryRepository
import com.asep.storyapp.data.repository.UserRepository

object Injection {

    fun provideUserRepository(dataStore: DataStore<Preferences>): UserRepository {
        val api = StoryApi.retrofitService
        val pref = UserDataStore.getInstance(dataStore)
        return UserRepository.getInstance(api, pref)
    }

    fun provideStoryRepository(context: Context): StoryRepository {
        val database = StoryDatabase.getDatabase(context)
        val api = StoryApi.retrofitService
        return StoryRepository(database, api)
    }

}