package com.dbtechprojects.dummyuserapi.ui.userList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dbtechprojects.dummyuserapi.di.DispatcherProvider
import com.dbtechprojects.dummyuserapi.repository.MainRepository
import com.dbtechprojects.dummyuserapi.ui.userList.state.UserListAction
import com.dbtechprojects.dummyuserapi.ui.userList.state.UserListViewState
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class UserListViewModel (
    private val repository: MainRepository,
    private val dispatcherProvider: DispatcherProvider
    ) : ViewModel() {

    val userIntent = Channel<UserListAction>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<UserListViewState>(UserListViewState.Idle)
    val state: StateFlow<UserListViewState>
        get() = _state

    private var pageNumber = 1

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is UserListAction.FetchUser -> {
                        fetchUser(pageNumber)
                        pageNumber++
                    }
                }
            }
        }
    }

    private fun fetchUser(page: Int) {
        viewModelScope.launch(dispatcherProvider.io) {
            _state.value = UserListViewState.Loading
            _state.value = try {
                UserListViewState.Users(repository.getUsers(page))
            } catch (e: Exception) {
                UserListViewState.Error(e.localizedMessage)
            }
        }
    }
}