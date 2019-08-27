package com.example.daggermvvmretrofit.data.rest

import com.example.daggermvvmretrofit.data.model.Repo
import io.reactivex.Single
import javax.inject.Inject

class RepoRepository @Inject constructor(private val repoService: RepoService) {

    fun getRepositories(): Single<List<Repo>> {
        return repoService.getRepositories()
    }

    fun getRepo(owner: String, name: String): Single<Repo> {
        return repoService.getRepo(owner, name)
    }

}