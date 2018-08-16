package com.prookie.kotlinmvp.base.mvp

import com.prookie.kotlinmvp.network.RxService
import java.util.HashMap

/**
 * BaseModel
 * Created by brin on 2018/6/13.
 */
open class BaseModel {

    /**
     * 返回服务接口对象实例
     *
     * @param clazz
     * @param <T>
     * @return
     */
    protected fun <T> createService(clazz: Class<T>): T = RxService.RETROFIT.createService(clazz)


}