package com.prookie.kotlinmvp.biz.constract

import com.prookie.kotlinmvp.base.BasePageResponse
import com.prookie.kotlinmvp.base.BaseResponse
import com.prookie.kotlinmvp.base.mvp.IBaseView
import com.prookie.kotlinmvp.bean.Article
import io.reactivex.Observable

/**
 * 协议：SearchResult
 * Created by brin on 2018-08-08.
 */

interface ISearchResultView : IBaseView {

    fun searchSuccess(page: BasePageResponse<Article>)
    fun searchFailure(errorMsg: String)

}

interface ISearchResultPresenter {
    fun searchList(page: Int, key: String)
}

interface ISearchResultModel {
    fun searchList(page: Int, key: String): Observable<BaseResponse<BasePageResponse<Article>>>
}
