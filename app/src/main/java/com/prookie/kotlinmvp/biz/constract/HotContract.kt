package com.prookie.kotlinmvp.biz.constract

import com.prookie.kotlinmvp.base.BaseResponse
import com.prookie.kotlinmvp.base.mvp.IBaseView
import com.prookie.kotlinmvp.bean.HotKey
import io.reactivex.Observable

/**
 * 协议：hot
 * Created by brin on 2018-08-07.
 */

interface IHotView : IBaseView {

    fun hotkeySuccess(hotkeys: List<HotKey>)

    fun hotkeyFailure(errorMsg: String?)

    fun hotWebsiteSuccess(hotkeys: List<HotKey>)

    fun hotWebsiteFailure(errorMsg: String?)

}

interface IHotPresenter {

    fun hotkeys()

    fun hotWebsite()

}

interface IHotModel {

    fun hotkeys(): Observable<BaseResponse<List<HotKey>>>

    fun hotWebsite(): Observable<BaseResponse<List<HotKey>>>

}