package com.symvox.githubuserlist.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

class GithubService {
    @GET("/search/users")
    fun getUserList(@Query("q") query: String, @Query("page") page: Int, @Query("per_page") pageSize: Int) : Observable<UserList>
}