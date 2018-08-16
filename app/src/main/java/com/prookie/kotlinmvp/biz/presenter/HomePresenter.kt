package com.prookie.kotlinmvp.biz.presenter

import com.prookie.kotlinmvp.base.BasePageResponse
import com.prookie.kotlinmvp.base.BaseResponse
import com.prookie.kotlinmvp.base.mvp.BasePresenter
import com.prookie.kotlinmvp.bean.Article
import com.prookie.kotlinmvp.biz.constract.IHomeModel
import com.prookie.kotlinmvp.biz.constract.IHomePresenter
import com.prookie.kotlinmvp.biz.constract.IHomeView
import com.prookie.kotlinmvp.biz.model.HomeModel
import com.prookie.kotlinmvp.network.ApiCallback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * HomePresenter
 * Created by brin on 2018-08-04.
 */
class HomePresenter : BasePresenter<IHomeView>(), IHomePresenter {

    private val homeModel: IHomeModel by lazy {
        HomeModel()
    }

    /**
     * 文章列表
     */
    override fun articles(page: Int) =
            homeModel.articles(page)
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


    /**
     * 收藏
     */
    override fun collect(id: Int) =
            homeModel.collect(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .throttleFirst(500, TimeUnit.MICROSECONDS)
                    .compose(getMvpView().bindLifecycle<BaseResponse<Any>>())
                    .subscribe(object : ApiCallback<BaseResponse<Any>>() {

                        override fun onStart(d: Disposable) {}

                        override fun onSuccess(modelBean: BaseResponse<Any>) = getMvpView().collectSuccess()

                        override fun onFailure(errorMsg: String) = getMvpView().collectFailure(errorMsg)

                        override fun onFinish() {}

                    })

}



