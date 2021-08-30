package com.dbtechprojects.dummyuserapi.ui.userlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dbtechprojects.dummyuserapi.di.DispatcherProvider
import com.dbtechprojects.dummyuserapi.models.responses.UserResponse
import com.dbtechprojects.dummyuserapi.repository.MainRepository
import com.yougetme.app.api.Resource
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class UserListViewModel (
    private val repository: MainRepository,
    private val dispatcherProvider: DispatcherProvider
    ) : ViewModel() {

    private val TAG = "UserListViewModel"

    private var _users = MutableLiveData<Resource<UserResponse>>()
    val users : LiveData<Resource<UserResponse>>
        get() = _users

    init {
        getUsers(0)
    }

    @InternalCoroutinesApi
    fun getUsers(page: Int){
        viewModelScope.launch(dispatcherProvider.io) {
            repository.getUsers(page).collect { response ->
                Log.d(TAG, "received users in viewmodel $response")
                _users.postValue(response)
            }
        }
    }
}