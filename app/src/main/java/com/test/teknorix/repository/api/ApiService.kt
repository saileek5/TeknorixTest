package com.test.teknorix.repository.api

import com.test.teknorix.repository.model.UserListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/users")
    suspend fun getUserList(@Query("page") page: Int) : Response<UserListResponse>
}