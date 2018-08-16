package com.prookie.kotlinmvp.biz.presenter

import com.prookie.kotlinmvp.base.BasePageResponse
import com.prookie.kotlinmvp.base.BaseResponse
import com.prookie.kotlinmvp.base.mvp.BasePresenter
import com.prookie.kotlinmvp.bean.Article
import com.prookie.kotlinmvp.biz.constract.ISearchResultPresenter
import com.prookie.kotlinmvp.biz.constract.ISearchResultView
import com.prookie.kotlinmvp.biz.model.SearchResultModel
import com.prookie.kotlinmvp.network.ApiCallback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * SearchResultPresenter
 * Created by brin on 2018-08-08.
 */
class SearchResultPresenter : BasePresenter<ISearchResultView>(), ISearchResultPresenter {

    private val searchModel: SearchResultModel by lazy { SearchResultModel() }

    override fun searchList(page: Int, key: String) =
            searchModel.searchList(page, key)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .throttleFirst(500, TimeUnit.MICROSECONDS)
                    .compose(getMvpView().bindLifecycle<BaseResponse<BasePageResponse<Article>>>())
                    .subscribe(object : ApiCallback<BaseResponse<BasePageResponse<Article>>>() {

                        override fun onStart(d: Disposable) = getMvpView().showLoading()

                        override fun onSuccess(modelBean: BaseResponse<BasePageResponse<Article>>) =
                                if (modelBean.data != null) getMvpView().searchSuccess(modelBean.data)
                                else getMvpView().searchFailure("")

                        override fun onFailure(errorMsg: String) = getMvpView().searchFailure(errorMsg)

                        override fun onFinish() = getMvpView().hideLoading()

                    })
}