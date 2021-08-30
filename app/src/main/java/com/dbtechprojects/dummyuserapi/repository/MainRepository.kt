package com.dbtechprojects.dummyuserapi.repository

import com.dbtechprojects.dummyuserapi.models.responses.UserResponse

interface MainRepository {

    suspend fun getUsers(page : Int): UserResponse
}