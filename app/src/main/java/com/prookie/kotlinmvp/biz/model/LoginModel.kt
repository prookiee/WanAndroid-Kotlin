package com.prookie.kotlinmvp.biz.model

import com.prookie.kotlinmvp.base.BaseResponse
import com.prookie.kotlinmvp.base.mvp.BaseModel
import com.prookie.kotlinmvp.bean.UserInfo
import com.prookie.kotlinmvp.biz.constract.ILoginModel
import com.prookie.kotlinmvp.network.ApiService
import io.reactivex.Observable


/**
 * LoginModel
 * Created by brin on 2018/6/13.
 */
class LoginModel : BaseModel(), ILoginModel {

    override fun login(username: String, password: String): Observable<BaseResponse<UserInfo>> =
            createService(ApiService::class.java).login(username, password)

    override fun registe(username: String, password: String): Observable<BaseResponse<UserInfo>> =
            createService(ApiService::class.java).register(username, password, password)


}