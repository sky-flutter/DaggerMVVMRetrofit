package com.example.daggermvvmretrofit.di.module

import com.example.daggermvvmretrofit.ui.activity.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMainActivity(): MainActivity


}