package com.prookie.kotlinmvp.base

import android.support.v4.widget.SwipeRefreshLayout

/**
 * SwipeRefreshActivity
 */
abstract class SwipeRefreshActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener {

    protected lateinit var swipeRefresh: SwipeRefreshLayout

    override fun initView() = autoRefresh()

    //首次进入页面自动刷新
    private fun autoRefresh() {
        //开启刷新
        swipeRefresh.post { swipeRefresh.isRefreshing = true }

        //5s后如果刷新还未结束，结束刷新
        Thread(Runnable {
            Thread.sleep(5000)
            if (swipeRefresh.isRefreshing) swipeRefresh.post { swipeRefresh.isRefreshing = false }
        }).start()
    }
}
