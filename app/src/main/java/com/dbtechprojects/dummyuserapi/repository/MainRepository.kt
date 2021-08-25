package com.dbtechprojects.dummyuserapi.repository

interface MainRepository {

    suspend fun getUsers(page : Int)
}