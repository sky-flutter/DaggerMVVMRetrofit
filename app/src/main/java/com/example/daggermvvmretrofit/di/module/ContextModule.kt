package com.example.daggermvvmretrofit.di.module

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

@Module
abstract class ContextModule {
    @Binds
    abstract fun bindContext(application: Application): Context
}