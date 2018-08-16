package com.prookie.kotlinmvp.biz.presenter

import com.prookie.kotlinmvp.base.BasePageResponse
import com.prookie.kotlinmvp.base.BaseResponse
import com.prookie.kotlinmvp.base.mvp.BasePresenter
import com.prookie.kotlinmvp.bean.Article
import com.prookie.kotlinmvp.bean.HotKey
import com.prookie.kotlinmvp.biz.constract.IHotModel
import com.prookie.kotlinmvp.biz.constract.IHotPresenter
import com.prookie.kotlinmvp.biz.constract.IHotView
import com.prookie.kotlinmvp.biz.model.HotModel
import com.prookie.kotlinmvp.network.ApiCallback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * HotPresenter
 * Created by brin on 2018-08-07.
 */
class HotPresenter : BasePresenter<IHotView>(), IHotPresenter {

    private val hotModel: IHotModel by lazy { HotModel() }

    override fun hotkeys() =
            hotModel.hotkeys()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .throttleFirst(500, TimeUnit.MICROSECONDS)
                    .compose(getMvpView().bindLifecycle<BaseResponse<List<HotKey>>>())
                    .subscribe(object : ApiCallback<BaseResponse<List<HotKey>>>() {

                        override fun onStart(d: Disposable) = getMvpView().showLoading()

                        override fun onSuccess(modelBean: BaseResponse<List<HotKey>>) =
                                if (modelBean.data != null) getMvpView().hotkeySuccess(modelBean.data)
                                else getMvpView().hotkeyFailure("")

                        override fun onFailure(errorMsg: String) = getMvpView().hotkeyFailure(errorMsg)

                        override fun onFinish() = getMvpView().hideLoading()

                    })

    override fun hotWebsite() =
            hotModel.hotWebsite()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .throttleFirst(500, TimeUnit.MICROSECONDS)
                    .compose(getMvpView().bindLifecycle<BaseResponse<List<HotKey>>>())
                    .subscribe(object : ApiCallback<BaseResponse<List<HotKey>>>() {

                        override fun onStart(d: Disposable) = getMvpView().showLoading()

                        override fun onSuccess(modelBean: BaseResponse<List<HotKey>>) =
                                if (modelBean.data != null) getMvpView().hotWebsiteSuccess(modelBean.data)
                                else getMvpView().hotWebsiteFailure("")

                        override fun onFailure(errorMsg: String) = getMvpView().hotWebsiteFailure(errorMsg)

                        override fun onFinish() = getMvpView().hideLoading()

                    })

}