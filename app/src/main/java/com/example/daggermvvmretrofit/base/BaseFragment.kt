package com.example.daggermvvmretrofit.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.DaggerFragment

abstract class BaseFragment(private var appCompatActivity: AppCompatActivity? = null) : DaggerFragment() {
    @LayoutRes
    protected abstract fun layoutRes(): Int

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater?.inflate(layoutRes(), container, false)
        return mView
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        appCompatActivity = context as AppCompatActivity
    }

    override fun onDetach() {
        super.onDetach()
        appCompatActivity = null
    }

}