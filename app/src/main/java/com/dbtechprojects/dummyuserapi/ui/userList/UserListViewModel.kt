package com.dbtechprojects.dummyuserapi.ui.userList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dbtechprojects.dummyuserapi.di.DispatcherProvider
import com.dbtechprojects.dummyuserapi.repository.MainRepository
import com.dbtechprojects.dummyuserapi.ui.userList.state.UserListAction
import com.dbtechprojects.dummyuserapi.ui.userList.state.UserListViewState
import com.dbtechprojects.dummyuserapi.util.Constants
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class UserListViewModel(
    private val repository: MainRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    val userIntent = Channel<UserListAction>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<UserListViewState>(UserListViewState.Idle)
    val state: StateFlow<UserListViewState>
        get() = _state

    // pagination variables
    private var pageNumber = 0
    private var userCount = 0
    private var totalUsers = 0
    private var shouldPaginate = true

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is UserListAction.FetchUser -> {
                        if (shouldPaginate) {
                            fetchUser(pageNumber)
                            pageNumber++
                        }
                    }
                }
            }
        }
    }

    private fun fetchUser(page: Int) {
        viewModelScope.launch(dispatcherProvider.io) {
            // check whether we should query the next set of users
            /*
                   if the users count is greater or equal to the total users  then we no longer need
                    to request more users
                   as all users have already been requested
            */
            Log.d("ViewModel", "UserCount : $userCount , TotalUsers : $totalUsers")
            if (userCount != 0 && userCount >= totalUsers ) {
                Log.d("ViewModel", "should not paginate" +
                        "usercount : $userCount , diff : ${totalUsers - Constants.limitOfUsersPerApiCall}")
                shouldPaginate = false
                _state.value = UserListViewState.QueryExhausted
            } else {
                _state.value = UserListViewState.Loading
                _state.value = try {
                    val users = repository.getUsers(page)
                    userCount += users.data.size
                    totalUsers = users.total
                    UserListViewState.Users(users)

                } catch (e: Exception) {
                    UserListViewState.Error(e.localizedMessage)
                }
            }
        }
    }
}