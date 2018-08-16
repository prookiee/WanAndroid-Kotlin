package com.prookie.kotlinmvp.biz.constract

import com.prookie.kotlinmvp.base.BasePageResponse
import com.prookie.kotlinmvp.base.BaseResponse
import com.prookie.kotlinmvp.base.mvp.IBaseView
import com.prookie.kotlinmvp.bean.Article
import io.reactivex.Observable

/**
 * 协议：文章
 * Created by brin on 2018-08-06.
 */
interface IArticleView : IBaseView {
    fun articlesSuccess(page: BasePageResponse<Article>)
    fun articlesFailure(errorMsg: String)

}

interface IArticlePresenter {
    fun articles(page: Int, cid: Int)
}

interface IArticleModel {
    fun articles(page: Int, cid: Int): Observable<BaseResponse<BasePageResponse<Article>>>
}