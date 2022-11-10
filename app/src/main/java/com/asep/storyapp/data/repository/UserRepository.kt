package com.asep.storyapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.asep.storyapp.data.datasource.local.datastore.UserDataStore
import com.asep.storyapp.data.datasource.remote.api.StoryService
import com.asep.storyapp.data.datasource.remote.response.GeneralResponse
import com.asep.storyapp.ui.domain.model.LoginResult
import com.asep.storyapp.ui.domain.model.User
import com.asep.storyapp.ui.domain.model.toDomain
import com.asep.storyapp.util.Result

class UserRepository(
    private val storyService: StoryService, private val userDataStore: UserDataStore
) {
    fun logIn(email: String, password: String): LiveData<Result<LoginResult>> =
        liveData {
            emit(Result.Loading())
            try {
                val response = storyService.postLogin(email, password)

                if (response.error == false) {
                    emit(Result.Success(response.loginResult!!.toDomain()))
                } else {
                    emit(Result.Error(response.message))
                }
            } catch (e: Exception) {
                emit(Result.Error(e.message))
            }
        }

    fun register(name: String, email: String, password: String): LiveData<Result<GeneralResponse>> =
        liveData {
            emit(Result.Loading())
            try {
                val response = storyService.postRegister(name, email, password)

                if (response.error == false) {
                    emit(Result.Success(response))
                } else {
                    emit(Result.Error(response.message))
                }
            } catch (e: Exception) {
                emit(Result.Error(e.message))
            }
        }

    suspend fun saveUser(user: User) = userDataStore.saveUser(user)

    fun getUser() = userDataStore.getUser()

    suspend fun logOut() = userDataStore.logOut()

    companion object {
        private var INSTANCE: UserRepository? = null
        fun getInstance(storyService: StoryService, userDataStore: UserDataStore): UserRepository {
            return INSTANCE ?: synchronized(this) {
                UserRepository(storyService, userDataStore).also {
                    INSTANCE = it
                }
            }
        }
    }
}