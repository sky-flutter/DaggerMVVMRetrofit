package com.example.daggermvvmretrofit.fragment_helper

import android.os.Bundle
import android.support.annotation.UiThread
import android.support.v4.app.Fragment
import android.view.View
import android.support.v4.util.Pair


@UiThread
interface ActivityBuilder {

    fun start()

    fun addBundle(bundle: Bundle): ActivityBuilder

    fun addSharedElements(pairs: List<Pair<View, String>>): ActivityBuilder

    fun byFinishingCurrent(): ActivityBuilder

    fun byFinishingAll(): ActivityBuilder

    fun <T : Fragment> setPage(page: Class<T>): ActivityBuilder

    fun forResult(requestCode: Int): ActivityBuilder

    fun shouldAnimate(isAnimate: Boolean): ActivityBuilder


}
