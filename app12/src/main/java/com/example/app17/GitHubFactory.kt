package com.example.app17

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object GithubFactory {
    private const val BASE_URL = "https://api.github.com/"

    fun makeGithubService(): GitHubService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(GitHubService::class.java)
}