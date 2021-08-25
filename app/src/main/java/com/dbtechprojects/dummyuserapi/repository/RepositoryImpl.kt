package com.dbtechprojects.dummyuserapi.repository

import android.util.Log
import com.dbtechprojects.dummyuserapi.api.UserApi

class RepositoryImpl(private val userApi: UserApi) : MainRepository {

    private val TAG = "RepositoryImpl"

    override suspend fun getUsers(page: Int){
        val response = userApi.getUsers(page)
        Log.d(TAG, response.body().toString())
    }
}