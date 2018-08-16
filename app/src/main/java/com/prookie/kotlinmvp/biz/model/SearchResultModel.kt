package com.prookie.kotlinmvp.biz.model

import com.prookie.kotlinmvp.base.BasePageResponse
import com.prookie.kotlinmvp.base.BaseResponse
import com.prookie.kotlinmvp.base.mvp.BaseModel
import com.prookie.kotlinmvp.bean.Article
import com.prookie.kotlinmvp.biz.constract.ISearchResultModel
import com.prookie.kotlinmvp.network.ApiService
import io.reactivex.Observable

/**
 * SearchResultModel
 * Created by brin on 2018-08-08.
 */
class SearchResultModel : BaseModel(), ISearchResultModel {

    override fun searchList(page: Int, key: String): Observable<BaseResponse<BasePageResponse<Article>>> =
            createService(ApiService::class.java).searchList(page, key)
}