package com.prookie.kotlinmvp.biz.presenter

import com.prookie.kotlinmvp.base.BaseResponse
import com.prookie.kotlinmvp.base.mvp.BasePresenter
import com.prookie.kotlinmvp.bean.UserInfo
import com.prookie.kotlinmvp.biz.constract.ILoginView
import com.prookie.kotlinmvp.biz.constract.ILoingPresenter
import com.prookie.kotlinmvp.biz.model.LoginModel
import com.prookie.kotlinmvp.network.ApiCallback

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


/**
 * LoginPresenter
 * Created by brin on 2018/6/13.
 */
class LoginPresenter : BasePresenter<ILoginView>(), ILoingPresenter {


    private val loginModel: LoginModel by lazy { LoginModel() }


    override fun Login(username: String, password: String) =
            loginModel.login(username, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .throttleFirst(500, TimeUnit.MICROSECONDS)
                    .compose(getMvpView().bindLifecycle<BaseResponse<UserInfo>>())
                    .subscribe(object : ApiCallback<BaseResponse<UserInfo>>() {

                        override fun onStart(d: Disposable) = getMvpView().showLoading()

                        override fun onSuccess(modelBean: BaseResponse<UserInfo>) =
                                if (modelBean.data != null) getMvpView().loginSuccess(modelBean.data)
                                else getMvpView().loginFailure("")

                        override fun onFailure(errorMsg: String) = getMvpView().loginFailure(errorMsg)

                        override fun onFinish() = getMvpView().hideLoading()

                    })


    override fun register(username: String, password: String) =
            loginModel.registe(username, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .throttleFirst(500, TimeUnit.MICROSECONDS)
                    .compose(getMvpView().bindLifecycle<BaseResponse<UserInfo>>())
                    .subscribe(object : ApiCallback<BaseResponse<UserInfo>>() {

                        override fun onStart(d: Disposable) = getMvpView().showLoading()

                        override fun onSuccess(modelBean: BaseResponse<UserInfo>) =
                                if (modelBean.data != null) getMvpView().loginSuccess(modelBean.data)
                                else getMvpView().loginFailure("")

                        override fun onFailure(errorMsg: String) = getMvpView().loginFailure(errorMsg)

                        override fun onFinish() = getMvpView().hideLoading()

                    })


}

