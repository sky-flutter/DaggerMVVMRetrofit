package com.example.daggermvvmretrofit.fragment_helper

interface Passable<in T> {

    fun passData(t: T)

}
