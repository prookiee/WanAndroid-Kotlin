package com.prookie.kotlinmvp.biz.model

import com.prookie.kotlinmvp.base.BaseResponse
import com.prookie.kotlinmvp.base.mvp.BaseModel
import com.prookie.kotlinmvp.bean.Category
import com.prookie.kotlinmvp.biz.constract.ICategoryModel
import com.prookie.kotlinmvp.network.ApiService
import io.reactivex.Observable

/**
 * CategoryModel
 * Created by brin on 2018-08-05.
 */
class CategoryModel : BaseModel(), ICategoryModel {

    override fun treeList(): Observable<BaseResponse<List<Category>>> = createService(ApiService::class.java).categoryTreeList()


}