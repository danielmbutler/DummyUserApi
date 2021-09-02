package com.dbtechprojects.dummyuserapi.ui.userList

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.dbtechprojects.dummyuserapi.databinding.ActivityMainBinding
import com.dbtechprojects.dummyuserapi.di.Injection
import com.dbtechprojects.dummyuserapi.models.User
import com.dbtechprojects.dummyuserapi.models.responses.UserResponse
import com.dbtechprojects.dummyuserapi.ui.userList.state.UserListAction
import com.dbtechprojects.dummyuserapi.ui.userList.state.UserListViewState
import com.dbtechprojects.dummyuserapi.util.Constants
import com.dbtechprojects.dummyuserapi.util.ViewUtils
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    @InternalCoroutinesApi
    private lateinit var viewModel: UserListViewModel
    private lateinit var adapter: UserListAdapter
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val TAG = "MainActivity"
    private var userList = mutableListOf<User>()

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
        setupClicks()

        lifecycleScope.launchWhenStarted {
            observeViewModel()
        }
    }

    @InternalCoroutinesApi
    private fun setupClicks() {
        binding.listViewGetUserButton.setOnClickListener {
            lifecycleScope.launch {
                viewModel.userIntent.send(UserListAction.FetchUser)
            }
        }
    }


    @InternalCoroutinesApi
    private fun setupRv() {
        adapter = UserListAdapter()
        binding.listViewRv.adapter = adapter
        binding.listViewRv.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
                    // recyclerview is at the bottom so lets fetch more users
                    lifecycleScope.launch {
                        viewModel.userIntent.send(UserListAction.FetchUser)
                    }
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @InternalCoroutinesApi
    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect { viewState ->
                when (viewState) {
                    is UserListViewState.Idle -> {
                    }

                    is UserListViewState.Loading -> {
                        ViewUtils.showProgress(this@MainActivity)
                    }

                    is UserListViewState.Error -> {
                        ViewUtils.showErrorSnackBar(binding.root, viewState.error)
                        binding.listViewGetUserButton.visibility = View.VISIBLE
                        ViewUtils.progressDismiss(this@MainActivity)
                    }

                    is UserListViewState.Users -> {
                        ViewUtils.progressDismiss(this@MainActivity)
                        binding.listViewGetUserButton.visibility = View.GONE
                        binding.listViewRv.visibility = View.VISIBLE
                        renderList(viewState.user)
                    }
                    is UserListViewState.QueryExhausted -> {
                        addEndOfUsersMessage()
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun renderList(users: UserResponse) {
        userList.addAll(users.data)
        adapter.updateList(userList)
    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun addEndOfUsersMessage() {
        val fake = User(
            id = "",
            firstName = "No Users",
            lastName = "Remaining",
            title = "",
            picture = ""
        )
        userList.add(fake)
        adapter.updateList(userList)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}