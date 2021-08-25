package com.dbtechprojects.dummyuserapi.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.dbtechprojects.dummyuserapi.R
import com.dbtechprojects.dummyuserapi.di.DispatcherImpl
import com.dbtechprojects.dummyuserapi.di.Injection
import com.dbtechprojects.dummyuserapi.ui.viewmodels.UserListViewModel

class MainActivity : AppCompatActivity() {
    private var viewModel: UserListViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(
            this, Injection.provideViewModelFactory(
                context = this,
            )
        )
            .get(UserListViewModel::class.java)
    }
}