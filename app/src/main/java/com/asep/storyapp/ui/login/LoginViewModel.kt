package com.asep.storyapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.asep.storyapp.data.repository.UserRepository
import com.asep.storyapp.ui.domain.model.LoginResult
import com.asep.storyapp.util.Result

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun signIn(email: String, password: String): LiveData<Result<LoginResult>> =
        userRepository.logIn(email, password)
}