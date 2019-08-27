package com.example.daggermvvmretrofit.base

import com.example.daggermvvmretrofit.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class BaseApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val application = DaggerAppComponent.builder().application(this).build()
        application.inject(this)
        return application

    }

}