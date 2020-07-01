package com.example.app17

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MontandoViewModel : ViewModel() {

    private val repositories: MutableLiveData<List<Repository>> by lazy {
        MutableLiveData<List<Repository>>()
    }

    fun getRepositories(): LiveData<List<Repository>> {
        return repositories
    }

    fun requestRepositories() {
        viewModelScope.launch {
            loadRepositories()
        }
    }

    private suspend fun loadRepositories() {
        val service = GithubFactory.makeGithubService()
        val response = service.getRepos()

        repositories.value = response.body()
    }
}