package com.example.app17

import retrofit2.Response
import retrofit2.http.GET

data class Repository (
    val name: String,
    val html_url: String
)

interface GitHubService {
    @GET("users/diego3g/repos/")
    suspend fun getRepos(): Response<List<Repository>>
}