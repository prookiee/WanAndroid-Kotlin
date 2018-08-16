package com.prookie.kotlinmvp.biz.presenter

import com.prookie.kotlinmvp.base.BasePageResponse
import com.prookie.kotlinmvp.base.BaseResponse
import com.prookie.kotlinmvp.base.mvp.BasePresenter
import com.prookie.kotlinmvp.bean.Article
import com.prookie.kotlinmvp.biz.constract.*
import com.prookie.kotlinmvp.biz.model.ArticleListModel
import com.prookie.kotlinmvp.network.ApiCallback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * ArticleListPresenter
 * Created by brin on 2018-08-06.
 */
class ArticleListPresenter : BasePresenter<IArticleView>(), IArticlePresenter {

    private val articleModel: IArticleModel by lazy {
        ArticleListModel()
    }

    /**
     * 文章列表
     */
    override fun articles(page: Int, cid: Int) =
            articleModel.articles(page, cid)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .throttleFirst(500, TimeUnit.MICROSECONDS)
                    .compose(getMvpView().bindLifecycle<BaseResponse<BasePageResponse<Article>>>())
                    .subscribe(object : ApiCallback<BaseResponse<BasePageResponse<Article>>>() {

                        override fun onStart(d: Disposable) = getMvpView().showLoading()

                        override fun onSuccess(modelBean: BaseResponse<BasePageResponse<Article>>) =
                                if (modelBean.data != null) getMvpView().articlesSuccess(modelBean.data)
                                else getMvpView().articlesFailure("")

                        override fun onFailure(errorMsg: String) = getMvpView().articlesFailure(errorMsg)

                        override fun onFinish() = getMvpView().hideLoading()

                    })


}



