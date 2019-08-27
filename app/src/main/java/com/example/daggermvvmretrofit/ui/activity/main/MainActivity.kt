package com.example.daggermvvmretrofit.ui.activity.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import com.example.daggermvvmretrofit.R
import com.example.daggermvvmretrofit.fragment_helper.FragmentFactory
import com.example.daggermvvmretrofit.fragment_helper.FragmentNavigationFactory
import com.example.daggermvvmretrofit.fragment_helper.FragmentNavigationFactory_Factory
import com.example.daggermvvmretrofit.ui.activity.list.ListRepoFragment
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var mMainViewModel: MainViewModel

    lateinit var fragmentNavigationFactory: FragmentNavigationFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

/*
        supportFragmentManager.beginTransaction()
                .replace(R.id.fl_main_container, ListRepoFragment())
                .commit()
*/

        fragmentNavigationFactory = FragmentNavigationFactory(this)
        fragmentNavigationFactory.make(ListRepoFragment()).replace(false)
    }

    fun <F : Fragment> getCurrentFragment(): Fragment? {
        return FragmentFactory.getCurrentFragment<Fragment>(this)
    }


    private fun findFragmentPlaceHolder(): Int {
        return R.id.fl_main_container
    }




}
