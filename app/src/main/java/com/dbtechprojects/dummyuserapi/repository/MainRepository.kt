package com.dbtechprojects.dummyuserapi.repository

import com.dbtechprojects.dummyuserapi.models.responses.UserResponse
import com.yougetme.app.api.Resource
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    suspend fun getUsers(page : Int): Flow<Resource<UserResponse>>
}