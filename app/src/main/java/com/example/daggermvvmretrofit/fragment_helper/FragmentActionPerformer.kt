package com.example.daggermvvmretrofit.fragment_helper

import android.os.Bundle
import android.support.annotation.UiThread
import android.support.v4.util.Pair
import android.view.View

@UiThread
interface FragmentActionPerformer<T> {

    fun add(toBackStack: Boolean)

    fun add(toBackStack: Boolean, tag: String)

    fun replace(toBackStack: Boolean)

    fun replace(toBackStack: Boolean, tag: String)

    fun setBundle(bundle: Bundle): FragmentActionPerformer<*>

    fun addSharedElements(sharedElements: List<Pair<View, String>>): FragmentActionPerformer<*>

    fun clearHistory(tag: String?): FragmentActionPerformer<*>

    fun hasData(passable: Passable<T>): FragmentActionPerformer<*>

}