package com.prookie.kotlinmvp.biz.model

import com.prookie.kotlinmvp.base.BasePageResponse
import com.prookie.kotlinmvp.base.BaseResponse
import com.prookie.kotlinmvp.base.mvp.BaseModel
import com.prookie.kotlinmvp.bean.Article
import com.prookie.kotlinmvp.biz.constract.IArticleModel
import com.prookie.kotlinmvp.network.ApiService
import io.reactivex.Observable

/**
 * ArticleListModel
 * Created by brin on 2018-08-06.
 */
class ArticleListModel : BaseModel(), IArticleModel {

    /**
     * 文章列表
     */
    override fun articles(page: Int, cid: Int): Observable<BaseResponse<BasePageResponse<Article>>> = createService(ApiService::class.java).articles(page, cid)
}