package com.asep.storyapp.ui

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.asep.storyapp.data.repository.StoryRepository
import com.asep.storyapp.data.repository.UserRepository
import com.asep.storyapp.di.Injection
import com.asep.storyapp.ui.home.HomeViewModel
import com.asep.storyapp.ui.login.LoginViewModel
import com.asep.storyapp.ui.maps.MapsViewModel
import com.asep.storyapp.ui.register.RegisterViewModel
import com.asep.storyapp.ui.storyadd.StoryAddViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_datastore")

class ViewModelFactory(
    private val userRepository: UserRepository, private val storyRepository: StoryRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(storyRepository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(StoryAddViewModel::class.java) -> {
                StoryAddViewModel(storyRepository) as T
            }
            modelClass.isAssignableFrom(SharedViewModel::class.java) -> {
                SharedViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                MapsViewModel(storyRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        private var INSTANCE: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                ViewModelFactory(
                    Injection.provideUserRepository(context.dataStore),
                    Injection.provideStoryRepository(context)
                ).also {
                    INSTANCE = it
                }
            }
        }
    }
}