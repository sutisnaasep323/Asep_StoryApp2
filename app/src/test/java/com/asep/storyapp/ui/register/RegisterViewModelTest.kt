package com.asep.storyapp.ui.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.asep.storyapp.data.datasource.remote.response.GeneralResponse
import com.asep.storyapp.data.repository.UserRepository
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
class RegisterViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var userRepository: UserRepository
    private lateinit var registerViewModel: RegisterViewModel
    private val dummyRegisterResponse = DataDummy.generateDummyGeneralResponse()

    private val dummyName = "name"
    private val dummyEmail = "email@mail.com"
    private val dummyPassword = "password"

    @Before
    fun setUp() {
        registerViewModel = RegisterViewModel(userRepository)
    }

    @Test
    fun `Register Success with success result`() {
        val expectedResponse = MutableLiveData<Result<GeneralResponse>>()
        expectedResponse.value = Result.Success(dummyRegisterResponse)

        `when`(userRepository.register(dummyName, dummyEmail, dummyPassword)).thenReturn(
            expectedResponse
        )

        val actualResponse =
            registerViewModel.register(dummyName, dummyEmail, dummyPassword).getOrAwaitValue()

        Mockito.verify(userRepository).register(dummyName, dummyEmail, dummyPassword)

        Assert.assertNotNull(actualResponse)
        Assert.assertTrue(actualResponse is Result.Success)
        Assert.assertEquals(dummyRegisterResponse, (actualResponse as Result.Success).data)
    }

    @Test
    fun `Register Failed with error result`() {
        val expectedResponse = MutableLiveData<Result<GeneralResponse>>()
        expectedResponse.value = Result.Error("Error")

        `when`(userRepository.register(dummyName, dummyEmail, dummyPassword)).thenReturn(
            expectedResponse
        )

        val actualResponse =
            registerViewModel.register(dummyName, dummyEmail, dummyPassword).getOrAwaitValue()

        Mockito.verify(userRepository).register(dummyName, dummyEmail, dummyPassword)

        Assert.assertNotNull(actualResponse)
        Assert.assertTrue(actualResponse is Result.Error)
        Assert.assertEquals(
            expectedResponse.value?.message,
            actualResponse.message
        )
    }
}