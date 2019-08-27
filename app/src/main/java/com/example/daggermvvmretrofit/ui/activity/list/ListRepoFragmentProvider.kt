package com.example.daggermvvmretrofit.ui.activity.list

import android.support.v4.app.ListFragment
import com.example.daggermvvmretrofit.di.module.FragmentViewModelProvider
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ListRepoFragmentProvider {
    @ContributesAndroidInjector(modules = [FragmentViewModelProvider::class])
    abstract fun provideListRepoFragmentFactory(): ListFragment

    /*@ContributesAndroidInjector
    abstract fun provideListRepoFragmentFactory(): ListFragment*/

}