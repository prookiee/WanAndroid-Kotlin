package com.prookie.kotlinmvp.base.mvp

/**
 * IBasePresenter
 * Created by brin on 2018/6/13.
 */
interface IBasePresenter<in T : IBaseView> {

    fun attachView(view: T)

    fun detachView()

}