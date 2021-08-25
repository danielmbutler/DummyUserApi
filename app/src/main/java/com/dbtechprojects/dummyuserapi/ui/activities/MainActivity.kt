package com.dbtechprojects.dummyuserapi.ui.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dbtechprojects.dummyuserapi.databinding.ActivityMainBinding
import com.dbtechprojects.dummyuserapi.di.Injection
import com.dbtechprojects.dummyuserapi.ui.adapters.UserListAdapter
import com.dbtechprojects.dummyuserapi.ui.viewmodels.UserListViewModel
import com.dbtechprojects.dummyuserapi.util.ViewUtils
import com.yougetme.app.api.Resource
import kotlinx.coroutines.InternalCoroutinesApi

class MainActivity : AppCompatActivity() {
    @InternalCoroutinesApi
    private lateinit var viewModel: UserListViewModel
    private lateinit var adapter: UserListAdapter
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private  val TAG = "MainActivity"

    @RequiresApi(Build.VERSION_CODES.N)
    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory()).get(UserListViewModel::class.java)
        setupRv()

        lifecycleScope.launchWhenStarted {
            initObservers()
        }
    }

    private fun setupRv() {
        adapter = UserListAdapter()
        binding.listViewRv.adapter = adapter
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @InternalCoroutinesApi
    private fun initObservers() {
        viewModel.users.observe(this, { userResponse ->
            when(userResponse){
                is Resource.Loading -> {ViewUtils.showProgress(this)}
                is Resource.Success -> {
                    ViewUtils.progressDismiss(this)
                    userResponse.data?.let { userList ->
                        Log.d(TAG, userList.toString())
                        adapter.setDataSet(userList)
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
}