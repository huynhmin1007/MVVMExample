package com.example.androidmvvm.ui.view.usecase.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidmvvm.R
import com.example.androidmvvm.data.api.app.RetrofitInstance
import com.example.androidmvvm.data.api.app.service.UserApiService
import com.example.androidmvvm.data.model.User
import com.example.androidmvvm.databinding.FragmentHomeBinding
import com.example.androidmvvm.ui.adapter.UserAdapter
import com.example.androidmvvm.ui.custom.support.MarginItemDecoration
import com.example.androidmvvm.ui.data.Resource
import com.example.androidmvvm.ui.viewmodel.UserViewModel
import com.example.androidmvvm.ui.viewmodel.ViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    private lateinit var userViewModel: UserViewModel

    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userViewModel = ViewModelProvider(
            this,
            ViewModelFactory(RetrofitInstance.createService(UserApiService::class.java))
        ).get(UserViewModel::class.java)

        userViewModel.fetchUserList()
        arguments?.let {
            val user = HomeFragmentArgs.fromBundle(it).user
            if (user != null) {
                userViewModel.updateUser(Resource.Success(user))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupObservers() {
        setupFetchUserListObserver()
    }

    private fun setupFetchUserListObserver() {
        userViewModel.userList.observe(viewLifecycleOwner, Observer { usersResource ->
            if (usersResource == null)
                return@Observer

            when (usersResource) {
                is Resource.Loading -> {
                    binding.pbUsers.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.pbUsers.visibility = View.GONE
                    renderUserList(usersResource.data)
                }

                is Resource.Failure -> {
                    binding.pbUsers.visibility = View.GONE
                }
            }
        })
    }

    private fun renderUserList(data: List<User>) {
        userAdapter.addData(data)
    }

    private fun setupUI() {
        userViewModel.user.observe(viewLifecycleOwner) { userResource ->
            if (userResource is Resource.Success) {
                binding.tvUserDisplayName.text = userResource.data?.name
            }
        }
        setupUserRecycleView()
    }

    private fun setupUserRecycleView() {
        userAdapter = UserAdapter(arrayListOf())
        val recycleView = binding.rcvUsers

        recycleView.layoutManager = LinearLayoutManager(requireContext())
        recycleView.addItemDecoration(
            MarginItemDecoration(
                spaceSize = resources.getDimensionPixelSize(
                    R.dimen.item_margin
                )
            )
        )
        recycleView.adapter = userAdapter
    }
}