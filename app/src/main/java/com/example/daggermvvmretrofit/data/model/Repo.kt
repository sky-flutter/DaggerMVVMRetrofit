package com.example.daggermvvmretrofit.data.model

import com.google.gson.annotations.SerializedName

class Repo(
        val id: Long,
        val name: String,
        val description: String,
        val owner: User,
        @SerializedName("stargazers_count")
        val stars: Long,
        @SerializedName("forks_count")
        val forks: Long
)