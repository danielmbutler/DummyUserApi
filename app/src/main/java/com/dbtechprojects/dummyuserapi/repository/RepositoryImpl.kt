package com.dbtechprojects.dummyuserapi.repository

import com.dbtechprojects.dummyuserapi.api.UserApi
import com.dbtechprojects.dummyuserapi.models.responses.UserResponse

class RepositoryImpl(private val userApi: UserApi) : MainRepository {

    private val TAG = "RepositoryImpl"

    override suspend fun getUsers(page: Int) :UserResponse = userApi.getUsers(page)
}