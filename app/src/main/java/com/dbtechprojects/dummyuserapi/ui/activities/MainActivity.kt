package com.dbtechprojects.dummyuserapi.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dbtechprojects.dummyuserapi.R
import com.dbtechprojects.dummyuserapi.databinding.ActivityMainBinding
import com.dbtechprojects.dummyuserapi.di.Injection
import com.dbtechprojects.dummyuserapi.ui.viewmodels.UserListViewModel
import com.dbtechprojects.dummyuserapi.util.ViewUtils
import com.yougetme.app.api.Resource
import kotlinx.coroutines.InternalCoroutinesApi

class MainActivity : AppCompatActivity() {
    @InternalCoroutinesApi
    private lateinit var viewModel: UserListViewModel
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private  val TAG = "MainActivity"
    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory()).get(UserListViewModel::class.java)

        lifecycleScope.launchWhenStarted {
            initObservers()
        }
    }

    @InternalCoroutinesApi
    private fun initObservers() {
        viewModel.users.observe(this, { userResponse ->
            when(userResponse){
                is Resource.Loading -> {ViewUtils.showProgress(this)}
                is Resource.Success -> {
                    userResponse.data?.let {
                        Log.d(TAG, it.toString())}
                    }
                is Resource.Error -> {
                    Log.d(TAG, "${userResponse.error}")
                }
            }
        })
    }
}