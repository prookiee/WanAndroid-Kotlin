package com.prookie.kotlinmvp.biz.presenter

import com.prookie.kotlinmvp.base.BaseResponse
import com.prookie.kotlinmvp.base.mvp.BasePresenter
import com.prookie.kotlinmvp.bean.Category
import com.prookie.kotlinmvp.biz.constract.ICategoryModel
import com.prookie.kotlinmvp.biz.constract.ICategoryPresenter
import com.prookie.kotlinmvp.biz.constract.ICategoryView
import com.prookie.kotlinmvp.biz.model.CategoryModel
import com.prookie.kotlinmvp.network.ApiCallback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * CategoryPresenter
 * Created by brin on 2018-08-05.
 */
class CategoryPresenter : BasePresenter<ICategoryView>(), ICategoryPresenter {

    private val categoryModel: ICategoryModel by lazy {
        CategoryModel()
    }

    override fun treeList() =
            categoryModel.treeList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(getMvpView().bindLifecycle<BaseResponse<List<Category>>>())
                    .subscribe(object : ApiCallback<BaseResponse<List<Category>>>() {

                        override fun onStart(d: Disposable) = getMvpView().showLoading()

                        override fun onSuccess(modelBean: BaseResponse<List<Category>>) =
                                if (modelBean.data != null) getMvpView().treeListSuccess(modelBean.data)
                                else getMvpView().treeListFailure("")

                        override fun onFailure(errorMsg: String) = getMvpView().treeListFailure(errorMsg)

                        override fun onFinish() = getMvpView().hideLoading()

                    })

}