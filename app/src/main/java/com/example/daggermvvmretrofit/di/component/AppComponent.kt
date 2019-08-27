package com.example.daggermvvmretrofit.di.component

import com.example.daggermvvmretrofit.base.BaseApplication
import com.example.daggermvvmretrofit.di.module.ActivityModule
import com.example.daggermvvmretrofit.di.module.AppModule
import com.example.daggermvvmretrofit.di.module.ContextModule
import com.example.daggermvvmretrofit.di.module.FragmentModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivityModule::class,
    ContextModule::class,
    FragmentModule::class
])
interface AppComponent : AndroidInjector<DaggerApplication> {

    fun inject(instance: BaseApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(daggerApplication: DaggerApplication): Builder

        fun build(): AppComponent
    }
}