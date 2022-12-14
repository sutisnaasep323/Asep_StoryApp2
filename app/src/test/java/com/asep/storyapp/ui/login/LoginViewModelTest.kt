package com.asep.storyapp.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.asep.storyapp.data.repository.UserRepository
import com.asep.storyapp.data.domain.model.LoginResult
import com.asep.storyapp.data.domain.model.toDomain
import com.asep.storyapp.util.Result
import com.asep.storyapp.utils.DataDummy
import com.asep.storyapp.utils.getOrAwaitValue
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var userRepository: UserRepository
    private lateinit var loginViewModel: LoginViewModel
    private val dummyLoginResponse = DataDummy.generateDummyLoginResponse()

    private val dummyEmail = "email@mail.com"
    private val dummyPassword = "password"

    @Before
    fun setUp() {
        loginViewModel = LoginViewModel(userRepository)
    }

    @Test
    fun `Login Success with success result`() {
        val expectedResponse = MutableLiveData<Result<LoginResult>>()
        expectedResponse.value = Result.Success(dummyLoginResponse.loginResult!!.toDomain())

        `when`(userRepository.logIn(dummyEmail, dummyPassword)).thenReturn(expectedResponse)

        val actualResponse =
            loginViewModel.signIn(dummyEmail, dummyPassword).getOrAwaitValue()

        Mockito.verify(userRepository).logIn(dummyEmail, dummyPassword)

        Assert.assertNotNull(actualResponse)
        Assert.assertTrue(actualResponse is Result.Success)
        Assert.assertEquals(
            dummyLoginResponse.loginResult!!.toDomain(),
            (actualResponse as Result.Success).data
        )
    }

    @Test
    fun `Login Failed with error result`() {
        val expectedResponse = MutableLiveData<Result<LoginResult>>()
        expectedResponse.value = Result.Error("Error")

        `when`(userRepository.logIn(dummyEmail, dummyPassword)).thenReturn(expectedResponse)

        val actualResponse = loginViewModel.signIn(dummyEmail, dummyPassword).getOrAwaitValue()

        Mockito.verify(userRepository).logIn(dummyEmail, dummyPassword)

        Assert.assertNotNull(actualResponse)
        Assert.assertTrue(actualResponse is Result.Error)
        Assert.assertEquals(
            expectedResponse.value?.message,
            actualResponse.message
        )
    }
}
