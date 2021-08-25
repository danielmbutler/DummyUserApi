package com.dbtechprojects.dummyuserapi.repository

import android.util.Log
import com.dbtechprojects.dummyuserapi.api.UserApi

class RepositoryImpl(private val userApi: UserApi) {

    private val TAG = "RepositoryImpl"

    suspend fun getUsers(){
        val response = userApi.getUsers(0)
        Log.d(TAG, response.body().toString())
    }
}