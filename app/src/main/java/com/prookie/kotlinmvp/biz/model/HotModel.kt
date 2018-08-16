package com.prookie.kotlinmvp.biz.model

import com.prookie.kotlinmvp.base.BaseResponse
import com.prookie.kotlinmvp.base.mvp.BaseModel
import com.prookie.kotlinmvp.bean.HotKey
import com.prookie.kotlinmvp.biz.constract.IHotModel
import com.prookie.kotlinmvp.network.ApiService
import io.reactivex.Observable

/**
 * HotModel
 * Created by brin on 2018-08-07.
 */
class HotModel : BaseModel(), IHotModel {
    override fun hotWebsite(): Observable<BaseResponse<List<HotKey>>> = createService(ApiService::class.java).hotWebsite()

    override fun hotkeys(): Observable<BaseResponse<List<HotKey>>> = createService(ApiService::class.java).hotKeys()
}