package com.dbtechprojects.dummyuserapi.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dbtechprojects.dummyuserapi.di.DispatcherProvider
import com.dbtechprojects.dummyuserapi.repository.MainRepository
import com.dbtechprojects.dummyuserapi.ui.userlist.UserListViewModel
import kotlinx.coroutines.InternalCoroutinesApi

/**
 * Factory for ViewModels
 */
class ViewModelFactory(
    private val repository: MainRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModelProvider.NewInstanceFactory() {

    @InternalCoroutinesApi
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        UserListViewModel(repository, dispatcherProvider) as T


}