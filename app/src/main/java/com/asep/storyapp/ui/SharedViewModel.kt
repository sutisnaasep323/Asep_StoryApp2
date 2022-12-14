package com.asep.storyapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asep.storyapp.data.repository.UserRepository
import com.asep.storyapp.data.domain.model.User
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SharedViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun saveUser(user: User) {
        viewModelScope.launch {
            userRepository.saveUser(user)
            _user.value = user
        }
    }

    fun fetchUser() {
        viewModelScope.launch {
            _user.value = userRepository.getUser().first()
        }
    }

    fun logOut() {
        viewModelScope.launch {
            _user.value = User("", "", "", false)
            userRepository.logOut()
        }
    }

}