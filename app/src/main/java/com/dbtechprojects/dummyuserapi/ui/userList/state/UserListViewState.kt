package com.dbtechprojects.dummyuserapi.ui.userList.state


import com.dbtechprojects.dummyuserapi.models.responses.UserResponse

sealed class UserListViewState() {

    object Idle : UserListViewState()
    object Loading : UserListViewState()
    data class Users(val user: UserResponse) : UserListViewState()
    data class Error(val error: String?) : UserListViewState()
    object QueryExhausted : UserListViewState()

}