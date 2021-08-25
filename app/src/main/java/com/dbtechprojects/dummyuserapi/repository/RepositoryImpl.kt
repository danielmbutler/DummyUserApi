package com.dbtechprojects.dummyuserapi.repository

import com.dbtechprojects.dummyuserapi.api.UserApi
import com.dbtechprojects.dummyuserapi.models.User
import com.dbtechprojects.dummyuserapi.util.Constants
import com.yougetme.app.api.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RepositoryImpl(private val userApi: UserApi) : MainRepository {

    private val TAG = "RepositoryImpl"

    override suspend fun getUsers(page: Int) : Flow<Resource<List<User>>>{
        return  flow {
            this.emit(Resource.Loading())
            try {
                val response = userApi.getUsers(page)
                if (response.body()!= null){
                    if (response.body()!!.data.isNotEmpty()){
                        this.emit(Resource.Success<List<User>>(response.body()!!.data))
                    } else this.emit(Resource.Error<List<User>>(Constants.API_NO_USERS_ERROR))
                } else this.emit(Resource.Error<List<User>>(Constants.API_SERVER_ERROR))

            } catch (e: Exception){
                this.emit(Resource.Error<List<User>>(Constants.API_INTERNET_ERROR))
            }
        }
    }
}