package com.example.daggermvvmretrofit.fragment_helper

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

interface Navigator {
    fun <T : Fragment> load(tClass: Class<T>): FragmentActionPerformer<T>

    fun loadActivity(aClass: Class<out AppCompatActivity>): ActivityBuilder

    fun <T : Fragment> loadActivity(aClass: Class<out AppCompatActivity>, pageTClass: Class<T>): ActivityBuilder

    fun goBack()
}