package com.example.daggermvvmretrofit.fragment_helper

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.daggermvvmretrofit.R


object FragmentFactory {

    fun <T : Fragment> getFragment(aClass: Class<T>): T? {

        try {
            return aClass.newInstance()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

        return null
    }

    fun <F : Fragment> getCurrentFragment(context: Context): F? {
        return if (findFragmentPlaceHolder() == 0) null else (context as AppCompatActivity).supportFragmentManager.findFragmentById(findFragmentPlaceHolder()) as F?
    }

    private fun findFragmentPlaceHolder(): Int {
        return R.id.fl_main_container
    }
}
