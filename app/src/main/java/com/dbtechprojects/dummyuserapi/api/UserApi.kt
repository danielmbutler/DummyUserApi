package com.dbtechprojects.dummyuserapi.api

import com.dbtechprojects.dummyuserapi.models.responses.BaseApiResponse
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface UserApi {
    /**
     * Get repos ordered by stars.
     */
    @GET("user/?limit=20")
    suspend fun getUsers(
        @Query("page") page: Int,
    ): Response<BaseApiResponse>

    companion object {
        private const val BASE_URL = "https://dummyapi.io/data/v1/"
        private const val AppId = "6123dd6a79f73f0d1969e8eb"



        fun create(): UserApi {

            val client = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request: Request =
                        chain.request().newBuilder().addHeader("app-id", AppId).build()
                    return@addInterceptor chain.proceed(request)
                }

                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserApi::class.java)
        }
    }
}

// https://dummyapi.io/data/v1/user/?limit=20&page=0