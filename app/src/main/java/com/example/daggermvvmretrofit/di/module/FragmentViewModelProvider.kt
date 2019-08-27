package com.example.daggermvvmretrofit.di.module

import com.example.daggermvvmretrofit.data.rest.RepoRepository
import com.example.daggermvvmretrofit.ui.activity.list.ListRepoViewModel
import dagger.Module
import dagger.Provides

@Module
class FragmentViewModelProvider {

    @Provides
    fun providesListRepoFragmentModule(repository: RepoRepository): ListRepoViewModel {
        return ListRepoViewModel(repository)
    }

}