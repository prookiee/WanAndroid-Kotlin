package com.prookie.kotlinmvp.biz.constract

import io.reactivex.Observable
import com.prookie.kotlinmvp.base.BaseResponse
import com.prookie.kotlinmvp.base.mvp.IBaseView
import com.prookie.kotlinmvp.bean.UserInfo

/**
 * 协议：登录
 * Created by brin on 2018/6/13.
 */

interface ILoginView : IBaseView {
    fun loginSuccess(userInfo: UserInfo)
    fun loginFailure(errorMsg: String?)
    fun registerSuccess(userInfo: UserInfo)
    fun registerFailure(errorMsg: String?)
}

interface ILoingPresenter {
    fun Login(username: String, password: String)
    fun register(username: String, password: String)
}

interface ILoginModel {
    fun login(username: String, password: String): Observable<BaseResponse<UserInfo>>
    fun registe(username: String, password: String): Observable<BaseResponse<UserInfo>>
}