package com.example.daggermvvmretrofit.ui.activity.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.daggermvvmretrofit.data.model.Repo
import com.example.daggermvvmretrofit.data.rest.RepoRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject


class ListRepoViewModel @Inject constructor(private val repoRepository: RepoRepository) : ViewModel() {
    private var compositeDisposable: CompositeDisposable? = null
    var repos = MutableLiveData<List<Repo>>()
    var repoLoadError = MutableLiveData<Boolean>()
    var loading = MutableLiveData<Boolean>()


    init {
        compositeDisposable = CompositeDisposable()
        fetchRepos()
    }

    fun getRepos(): LiveData<List<Repo>> {
        return repos
    }

    fun getLoading(): LiveData<Boolean> {
        return loading
    }

    fun getRepoLoadError(): LiveData<Boolean> {
        return repoLoadError
    }

    fun fetchRepos() {
        loading.value = true
        val repoDisposable = repoRepository
                .getRepositories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Repo>>() {
                    override fun onSuccess(t: List<Repo>) {
                        repos.value = t
                        loading.value = false
                        repoLoadError.value = false
                    }

                    override fun onError(e: Throwable) {
                        repoLoadError.value = true
                        loading.value = false
                    }
                })
        compositeDisposable?.add(repoDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable?.clear()
        compositeDisposable = null
    }
}