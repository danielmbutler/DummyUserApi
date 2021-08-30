package com.dbtechprojects.dummyuserapi.ui.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.dbtechprojects.dummyuserapi.databinding.ActivityMainBinding
import com.dbtechprojects.dummyuserapi.di.Injection
import com.dbtechprojects.dummyuserapi.models.User
import com.dbtechprojects.dummyuserapi.ui.adapters.UserListAdapter
import com.dbtechprojects.dummyuserapi.ui.viewmodels.UserListViewModel
import com.dbtechprojects.dummyuserapi.util.Constants
import com.dbtechprojects.dummyuserapi.util.ViewUtils
import com.yougetme.app.api.Resource
import kotlinx.coroutines.InternalCoroutinesApi

class MainActivity : AppCompatActivity() {
    @InternalCoroutinesApi
    private lateinit var viewModel: UserListViewModel
    private lateinit var adapter: UserListAdapter
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val TAG = "MainActivity"

    //pagination variables
    private var userList = mutableListOf<User>()
    private var userCount = 0
    private var userRemainingCount = 0
    private var shouldPaginate = true
    private var pageNumber = 1


    @RequiresApi(Build.VERSION_CODES.N)
    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory()
        ).get(UserListViewModel::class.java)
        setupRv()

        lifecycleScope.launchWhenStarted {
            initObservers()
        }
    }

    @InternalCoroutinesApi
    private fun setupRv() {
        adapter = UserListAdapter()
        binding.listViewRv.adapter = adapter
        binding.listViewRv.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
                    if (shouldPaginate) {
                        pageNumber++
                        viewModel.getUsers(pageNumber)
                    }
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @InternalCoroutinesApi
    private fun initObservers() {
        viewModel.users.observe(this, { userResponse ->
            when (userResponse) {
                is Resource.Loading -> {
                    ViewUtils.showProgress(this)
                }
                is Resource.Success -> {
                    ViewUtils.progressDismiss(this)
                    userResponse.data?.let { response ->
                        response.data.let { userListFromApi ->
                            Log.d(TAG, userListFromApi.toString())

                            userList.addAll(userListFromApi)
                            adapter.updateList(userList)

                            userCount = userList.size
                            /*
                            if the users count is great or equal to the total users minus the amount of users the
                            app queries per api call then we no longer need to request more users
                            as all users have already been requested
                             */
                            Log.d(TAG, "userlistsize : ${userList.size}")
                            Log.d(TAG, "shouldPaginate $shouldPaginate")
                            if (userCount >= (response.total - Constants.limitOfUsersPerApiCall)){
                                shouldPaginate = false
                                addEndOfUsersMessage()
                            }
                        }

                    }
                }
                is Resource.Error -> {
                    Log.d(TAG, "${userResponse.error}")
                    ViewUtils.progressDismiss(this)
                    ViewUtils.showErrorSnackBar(binding.root, userResponse.error)
                }
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun addEndOfUsersMessage() {
        val fake = User(
            id = "",
            firstName = "No Users" ,
            lastName = "Remaining",
            title = "",
            picture = ""
        )
        userList.add(fake)
        adapter.updateList(userList)
    }
}