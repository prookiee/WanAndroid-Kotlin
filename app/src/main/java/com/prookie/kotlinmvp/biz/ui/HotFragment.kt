package com.prookie.kotlinmvp.biz.ui

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.prookie.kotlinmvp.R
import com.prookie.kotlinmvp.adapter.HotAdapter
import com.prookie.kotlinmvp.application.MainApplication
import com.prookie.kotlinmvp.base.BaseFragment
import com.prookie.kotlinmvp.base.SwipeRefreshFragment
import com.prookie.kotlinmvp.bean.Hot
import com.prookie.kotlinmvp.bean.HotKey
import com.prookie.kotlinmvp.biz.constract.IHotView
import com.prookie.kotlinmvp.biz.presenter.HotPresenter
import org.jetbrains.anko.toast

/**
 * HotAdapter
 * Created by brin on 2018-08-02.
 */
class HotFragment : SwipeRefreshFragment(), IHotView {

    companion object {
        private const val TAG = "HotFragment"
    }

    private val mHotList = mutableListOf<Hot>()
    private val mAdapter by lazy { HotAdapter(activity, mHotList) }
    private val mPresenter: HotPresenter by lazy { HotPresenter() }


    override fun getContentLayoutId(): Int = R.layout.fragment_hot

    override fun initView(view: View) {

        Log.e(TAG, "userInfo${MainApplication.instance().userInfo}")

        //findView
        swipeRefresh = view.findViewById(R.id.swipe_refresh_hot) as SwipeRefreshLayout
        val recyclerView = view.findViewById(R.id.rv_hot) as RecyclerView

        //init swipeRefreshLayout
        swipeRefresh.setOnRefreshListener(this)
        swipeRefresh.setColorSchemeColors(R.color.app_main_theme)

        //init RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = mAdapter

        mPresenter.attachView(this)

    }

    override fun initData() {
        mPresenter.hotkeys()
//        mPresenter.hotWebsite()
//        for (i in 0..10) {
//            val hotKeyList = mutableListOf<HotKey>()
//            for (j in 0..4) {
//                val hotKey = HotKey(1, "sered", "", 1, 1)
//                hotKeyList.add(hotKey)
//            }
//            val hot = Hot("你的", hotKeyList)
//            mHotList.add(hot)
//        }
//        mAdapter.notifyDataSetChanged()
    }

    override fun onRefresh() {
        initData()
    }

    override fun hideLoading() {
        super.hideLoading()
        if (swipeRefresh.isRefreshing) swipeRefresh.isRefreshing = false
    }


    override fun hotkeySuccess(hotkeys: List<HotKey>) {
        mHotList.clear()
        val hot = Hot(getString(R.string.hot_search), hotkeys)
        mHotList.add(hot)
        mAdapter.notifyDataSetChanged()
        mPresenter.hotWebsite()
    }

    override fun hotkeyFailure(errorMsg: String?) = activity.toast(errorMsg + "")


    override fun hotWebsiteSuccess(hotWebsites: List<HotKey>) {
        val hot = Hot(getString(R.string.hot_website), hotWebsites)
        mHotList.add(hot)
        mAdapter.notifyDataSetChanged()
    }

    override fun hotWebsiteFailure(errorMsg: String?) = hotkeyFailure(errorMsg + "")


}