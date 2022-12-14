package com.asep.storyapp.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.asep.storyapp.data.domain.model.toLoggedInUser
import com.asep.storyapp.ui.SharedViewModel
import com.asep.storyapp.ui.ViewModelFactory
import com.asep.storyapp.util.MIN_PASSWORD_LENGTH
import com.asep.storyapp.util.Result
import com.asep.storyapp.R
import com.asep.storyapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }
    private val sharedViewModel: SharedViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Current Page", "LoginFragment")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity).supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar!!.show()
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar!!.hide()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = viewModel
            loginFragment = this@LoginFragment
        }

        setObserver()
        playAnimation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setObserver() {
        sharedViewModel.user.observe(viewLifecycleOwner) { user ->
            if (user.isLogin) {
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.linearProgressIndicator.visibility = View.VISIBLE
            binding.btnSignIn.isEnabled = false
        } else {
            binding.linearProgressIndicator.visibility = View.GONE
            binding.btnSignIn.isEnabled = true
        }
    }

    fun goToRegisterScreen() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

    fun signIn() {
        if (formValidation()) {
            viewModel.signIn(
                binding.edLoginEmail.text.toString(),
                binding.edLoginPassword.text.toString()
            ).observe(viewLifecycleOwner) { loginResult ->
                when (loginResult) {
                    is Result.Loading -> {
                        setLoading(true)
                    }
                    is Result.Success -> loginResult.data?.let {
                        setLoading(false)
                        sharedViewModel.saveUser(it.toLoggedInUser())
                    }
                    is Result.Error -> {
                        setLoading(false)
                        Snackbar.make(binding.root, getString(R.string.oops), Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private fun formValidation(): Boolean {
        var isValid = true
        if (binding.edLoginEmail.text.isNullOrEmpty()) {
            binding.loginEmail.error = getString(R.string.required)
            isValid = false
        } else {
            if (!Patterns.EMAIL_ADDRESS.matcher(binding.edLoginEmail.text.toString()).matches()) {
                binding.loginEmail.error = getString(R.string.not_valid_email)
                isValid = false
            } else {
                binding.loginEmail.error = null
            }
        }

        if (binding.edLoginPassword.text.isNullOrEmpty()) {
            binding.loginPassword.error = getString(R.string.required)
            isValid = false
        } else if (binding.edLoginPassword.text?.length!! < MIN_PASSWORD_LENGTH) {
            binding.loginPassword.error = getString(R.string.minlength, MIN_PASSWORD_LENGTH)
            isValid = false
        } else {
            binding.loginPassword.error = null
        }
        return isValid
    }

    private fun playAnimation() {
        val logo = ObjectAnimator.ofFloat(binding.imgLoginLogo, View.ALPHA, 0f, 1f).setDuration(500)
        val loginEmail =
            ObjectAnimator.ofFloat(binding.loginEmail, View.ALPHA, 0f, 1f).setDuration(500)
        val loginPassword =
            ObjectAnimator.ofFloat(binding.loginPassword, View.ALPHA, 0f, 1f).setDuration(500)
        val btnSignIn = ObjectAnimator.ofFloat(binding.btnSignIn, View.ALPHA, 0f, 1f).setDuration(500)
        val lnrNotRegistered =
            ObjectAnimator.ofFloat(binding.lnrNotRegistered, View.ALPHA, 0f, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(logo, loginEmail, loginPassword, btnSignIn, lnrNotRegistered)
            start()
        }
    }
}