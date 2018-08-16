package com.prookie.kotlinmvp.biz.ui


import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log

import android.view.View
import com.prookie.kotlinmvp.R
import com.prookie.kotlinmvp.adapter.CategoryAdapter
import com.prookie.kotlinmvp.base.SwipeRefreshFragment
import com.prookie.kotlinmvp.bean.Category
import com.prookie.kotlinmvp.biz.constract.ICategoryView
import com.prookie.kotlinmvp.biz.presenter.CategoryPresenter
import org.jetbrains.anko.toast

/**
 * CategoryFragment
 * Created by brin on 2018/6/16.
 */
class CategoryFragment : SwipeRefreshFragment(), ICategoryView {


    companion object {
        private const val TAG = "CategoryFragment"

        fun newInstance(): CategoryFragment = CategoryFragment()

    }


    private val mCategoryList = mutableListOf<Category>()
    private val mAdapter: CategoryAdapter by lazy {
        CategoryAdapter(activity, mCategoryList)
    }

    private val mPresenter: CategoryPresenter by lazy {
        CategoryPresenter()
    }


    override fun getContentLayoutId(): Int = R.layout.fragment_category

    override fun initView(view: View) {
        //findView
        swipeRefresh = view.findViewById(R.id.swipeRefresh_category) as SwipeRefreshLayout
        val recyclerView = view.findViewById(R.id.rv_category) as RecyclerView

        //init swipeRefreshLayout
        swipeRefresh.setOnRefreshListener(this)
        swipeRefresh.setColorSchemeColors(R.color.app_main_theme)

        //init recyclerView
        recyclerView.run {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(activity)
        }


        //item 点击事件
        mAdapter.setOnItemClickListener { _, _, position ->

            CategoryArticleActivity.start(activity, mCategoryList[position].children)

        }

        mPresenter.attachView(this)
    }

    override fun initData() {
        mPresenter.treeList()
    }


    override fun onRefresh() {
        initData()
    }

//    val handle = object : Handler() {
//        override fun handleMessage(msg: Message?) {
//            msg.let {
//                swipeRefresh.isRefreshing = false
//            }
//        }
//    }


    override fun hideLoading() {
        super.hideLoading()
        swipeRefresh.isRefreshing = false
    }


    override fun treeListSuccess(categories: List<Category>) {
        mCategoryList.clear()
        mCategoryList.addAll(categories)
        mAdapter.notifyDataSetChanged()

    }

    override fun treeListFailure(errorMsg: String?) = activity.toast(errorMsg + "")

}