package com.dbtechprojects.dummyuserapi.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dbtechprojects.dummyuserapi.di.DispatcherProvider
import com.dbtechprojects.dummyuserapi.repository.MainRepository
import kotlinx.coroutines.launch

class UserListViewModel (
    private val repository: MainRepository,
    private val dispatcherProvider: DispatcherProvider
    ) : ViewModel() {

    init {
        getUsers(0)
    }

    fun getUsers(page: Int){
        viewModelScope.launch(dispatcherProvider.io) {
            repository.getUsers(page)
        }
    }
}