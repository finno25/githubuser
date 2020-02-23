package com.aura.githubuser.networks

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("search/users")
    fun getFindUsers(
        @Query("q") keyword: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Call<UsersResponse>?
}