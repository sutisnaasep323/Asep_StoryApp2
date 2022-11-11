package com.asep.storyapp.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.asep.storyapp.ui.ViewModelFactory
import com.asep.storyapp.util.MIN_PASSWORD_LENGTH
import com.asep.storyapp.util.Result
import com.asep.storyapp.R
import com.asep.storyapp.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = viewModel
            registerFragment = this@RegisterFragment
        }

        playAnimation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun playAnimation() {
        val logo = ObjectAnimator.ofFloat(binding.imgLogo, View.ALPHA, 0f, 1f).setDuration(500)
        val registerName =
            ObjectAnimator.ofFloat(binding.registerName, View.ALPHA, 0f, 1f).setDuration(500)
        val registerEmail =
            ObjectAnimator.ofFloat(binding.registerEmail, View.ALPHA, 0f, 1f).setDuration(500)
        val registerPassword =
            ObjectAnimator.ofFloat(binding.registerPassword, View.ALPHA, 0f, 1f).setDuration(500)
        val btnRegister =
            ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 0f, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(logo, registerName, registerEmail, registerPassword, btnRegister)
            start()
        }
    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.linearProgressIndicator.visibility = View.VISIBLE
            binding.btnRegister.isEnabled = false
        } else {
            binding.linearProgressIndicator.visibility = View.GONE
            binding.btnRegister.isEnabled = true
        }
    }

    fun register() {
        if (formValidation()) {
            with(viewModel) {
                register(
                    binding.edRegisterName.text.toString(),
                    binding.edRegisterEmail.text.toString(),
                    binding.edRegisterPassword.text.toString()
                ).observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Result.Loading -> {
                            setLoading(true)
                        }
                        is Result.Success -> {
                            setLoading(false)
                            Snackbar.make(
                                binding.root,
                                getString(R.string.success_register),
                                Snackbar.LENGTH_SHORT
                            ).show()
                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        }
                        is Result.Error -> {
                            setLoading(false)
                            Snackbar.make(
                                binding.root,
                                getString(R.string.oops),
                                Snackbar.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun formValidation(): Boolean {
        var isValid = true
        if (binding.edRegisterName.text.isNullOrEmpty()) {
            binding.registerName.error = getString(R.string.required)
            isValid = false
        } else {
            binding.registerName.error = null
        }

        if (binding.edRegisterEmail.text.isNullOrEmpty()) {
            binding.registerEmail.error = getString(R.string.required)
            isValid = false
        } else {
            if (!Patterns.EMAIL_ADDRESS.matcher(binding.edRegisterEmail.text.toString())
                    .matches()
            ) {
                binding.registerEmail.error = getString(R.string.not_valid_email)
                isValid = false
            } else {
                binding.registerEmail.error = null
            }
        }

        if (binding.edRegisterPassword.text.isNullOrEmpty()) {
            binding.registerPassword.error = getString(R.string.required)
            isValid = false
        } else if (binding.edRegisterPassword.text!!.length < MIN_PASSWORD_LENGTH) {
            binding.registerPassword.error = getString(R.string.minlength, MIN_PASSWORD_LENGTH)
            isValid = false
        } else {
            binding.registerPassword.error = null
        }
        return isValid
    }
}

