package com.example.daggermvvmretrofit.di.module

import com.example.daggermvvmretrofit.data.rest.RepoRepository
import com.example.daggermvvmretrofit.ui.activity.main.MainViewModel
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {
    @Provides
    fun providesMainActivityModule(repository: RepoRepository): MainViewModel {
        return MainViewModel(repository)
    }

}