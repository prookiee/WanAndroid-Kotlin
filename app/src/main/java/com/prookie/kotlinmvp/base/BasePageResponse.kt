package com.prookie.kotlinmvp.base

/**
 * Created by brin on 2018-08-04.
 */
class BasePageResponse<T>(
        var curPage: Int,
        var offset: Int,
        var over: Boolean,
        var pageCount: Int,
        var size: Int,
        var total: Int,
        var datas: List<T>)