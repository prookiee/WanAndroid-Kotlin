package com.prookie.kotlinmvp.biz.constract

import com.prookie.kotlinmvp.base.BaseResponse
import com.prookie.kotlinmvp.base.mvp.IBaseView
import com.prookie.kotlinmvp.bean.Category
import io.reactivex.Observable

/**
 *  协议：知识体系
 * Created by brin on 2018-08-05.
 */

interface ICategoryView : IBaseView{
    fun treeListSuccess(categories: List<Category>)
    fun treeListFailure(errorMsg: String?)
}


interface ICategoryPresenter {
    fun treeList()
}

interface ICategoryModel {
    fun treeList(): Observable<BaseResponse<List<Category>>>
}