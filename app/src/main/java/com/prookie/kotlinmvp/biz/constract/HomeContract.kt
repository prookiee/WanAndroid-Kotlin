package com.prookie.kotlinmvp.biz.constract

import com.prookie.kotlinmvp.base.BasePageResponse
import com.prookie.kotlinmvp.base.BaseResponse
import com.prookie.kotlinmvp.base.mvp.IBaseView
import com.prookie.kotlinmvp.bean.Article
import io.reactivex.Observable

/**
 * 首页 协议
 * Created by brin on 2018-08-04.
 */

interface IHomeView : IBaseView {
    fun articlesSuccess(articles: BasePageResponse<Article>)
    fun articlesFailure(errorMsg: String?)
    fun collectSuccess()
    fun collectFailure(errorMsg: String?)
}

interface IHomePresenter {
    fun articles(page: Int)
    fun collect(id: Int)
//    fun removeCollect(id:String)
}

interface IHomeModel {
    fun articles(page: Int): Observable<BaseResponse<BasePageResponse<Article>>>
    fun collect(id: Int): Observable<BaseResponse<Any>>
//    fun removeCollect(id: String)
}