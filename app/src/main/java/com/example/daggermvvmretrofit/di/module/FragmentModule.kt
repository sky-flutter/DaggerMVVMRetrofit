package com.example.daggermvvmretrofit.di.module

import com.example.daggermvvmretrofit.ui.activity.list.ListRepoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector(modules = [FragmentViewModelProvider::class])
    abstract fun bindListRepoFragment(): ListRepoFragment

}