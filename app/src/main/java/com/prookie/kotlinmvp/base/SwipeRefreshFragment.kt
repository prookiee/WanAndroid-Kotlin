package com.prookie.kotlinmvp.base

import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter


/**
 * SwipeRefreshFragment
 * Created by brin on 2018/7/4.
 */
abstract class SwipeRefreshFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    protected lateinit var swipeRefresh: SwipeRefreshLayout

    override fun initView(view: View) = autoRefresh()

    //首次进入页面自动刷新
    private fun autoRefresh() {
        //开启刷新
        swipeRefresh.post { swipeRefresh.isRefreshing = true }

        //5s后如果刷新还未结束，结束刷新
        Thread(Runnable {
            Thread.sleep(3000)
            if (swipeRefresh.isRefreshing) swipeRefresh.post { swipeRefresh.isRefreshing = false }
        }).start()
    }


    override fun onLoadMoreRequested() = Unit


}