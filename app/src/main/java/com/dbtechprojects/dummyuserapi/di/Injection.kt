package com.dbtechprojects.dummyuserapi.di


import androidx.lifecycle.ViewModelProvider
import com.dbtechprojects.dummyuserapi.api.UserApi
import com.dbtechprojects.dummyuserapi.repository.MainRepository
import com.dbtechprojects.dummyuserapi.repository.RepositoryImpl
import com.dbtechprojects.dummyuserapi.util.ViewModelFactory


// manual dependency Injection class
object Injection {

    private fun provideRepository(): MainRepository {
        return RepositoryImpl(UserApi.create())
    }

    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ViewModelFactory(provideRepository(), dispatcherProvider = DispatcherImpl)
    }
}