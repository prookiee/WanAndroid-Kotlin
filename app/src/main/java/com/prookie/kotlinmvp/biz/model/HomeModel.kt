package com.prookie.kotlinmvp.biz.model

import com.prookie.kotlinmvp.base.BasePageResponse
import com.prookie.kotlinmvp.base.BaseResponse
import com.prookie.kotlinmvp.base.mvp.BaseModel
import com.prookie.kotlinmvp.bean.Article
import com.prookie.kotlinmvp.biz.constract.IHomeModel
import com.prookie.kotlinmvp.network.ApiService
import io.reactivex.Observable

/**
 * HomeModel
 * Created by brin on 2018-08-04.
 */
class HomeModel : BaseModel(), IHomeModel {

    /**
     * 收藏文章
     */
    override fun collect(id: Int): Observable<BaseResponse<Any>> = createService(ApiService::class.java).collectArticle(id)

    /**
     * 文章列表
     */
    override fun articles(page: Int): Observable<BaseResponse<BasePageResponse<Article>>> = createService(ApiService::class.java).articles(page)
}