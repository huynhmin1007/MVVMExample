package com.example.androidmvvm.ui.view.usecase.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.androidmvvm.R
import com.example.androidmvvm.data.api.app.RetrofitInstance
import com.example.androidmvvm.data.api.app.service.UserApiService
import com.example.androidmvvm.data.model.User
import com.example.androidmvvm.databinding.FragmentLoginBinding
import com.example.androidmvvm.ui.data.Resource
import com.example.androidmvvm.ui.viewmodel.LoginViewModel
import com.example.androidmvvm.ui.viewmodel.ViewModelFactory

class LoginFragment : Fragment(), OnClickListener {

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding get() = _binding!!

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(RetrofitInstance.createService(UserApiService::class.java))
        ).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.btnLogin.id -> {
                binding.pbLogin.visibility = View.VISIBLE
                val email = binding.edtEmail.editText?.text.toString()
                val password = binding.edtPassword.editText?.text.toString()

                loginViewModel.login(email, password)
            }
        }
    }

    private fun setupObservers() {
        setupLoginObserver()
    }

    private fun setupLoginObserver() {
        val edtEmail = binding.edtEmail
        val edtPassword = binding.edtPassword
        val btnLogin = binding.btnLogin

        loginViewModel.loginFormState.observe(viewLifecycleOwner, Observer { loginFormState ->
            if (loginFormState == null)
                return@Observer

            btnLogin.isEnabled = loginFormState.isDataValid
            edtEmail.error = getString(loginFormState.emailError ?: R.string.empty)
            edtPassword.error = getString(loginFormState.passwordError ?: R.string.empty)
        })

        loginViewModel.loginResult.observe(viewLifecycleOwner, Observer { loginResult ->
            if (loginResult == null)
                return@Observer
            when (loginResult) {
                is Resource.Loading -> {
                    binding.pbLogin.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.pbLogin.visibility = View.GONE
                    handleLoginSuccess(loginResult.data)
                }
                is Resource.Failure -> {
                    binding.pbLogin.visibility = View.GONE
                    handleLoginFailure(loginResult.exception)
                }
            }
        })
    }

    private fun handleLoginSuccess(user: User) {
        binding.pbLogin.visibility = View.GONE

        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment(user)
        findNavController().navigate(action)
    }

    private fun handleLoginFailure(exception: Throwable) {
        Toast.makeText(
            requireContext(),
            exception.message ?: "Đăng nhập thất bại!",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun setupListeners() {
        setupClickListeners()
        setupInputListener()
    }

    private fun setupClickListeners() {
        binding.btnLogin.setOnClickListener(this)
    }

    private fun setupInputListener() {
        with(binding) {
            val edtEmail = edtEmail.editText!!
            val edtPassword = edtPassword.editText!!

            val textWatcher = object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    loginViewModel.loginDataChanged(
                        email =  edtEmail.text.toString(),
                        password = edtPassword.text.toString()
                    )
                }
            }

            edtEmail.addTextChangedListener(textWatcher)

            edtPassword.addTextChangedListener(textWatcher)
            edtPassword.setOnEditorActionListener { _, actionId, _ ->
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(
                        edtEmail.text.toString(),
                        edtPassword.text.toString())
                }
                false
            }
        }
    }
}