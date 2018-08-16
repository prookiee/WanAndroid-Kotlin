package com.prookie.kotlinmvp.biz.ui


import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log


import android.view.View
import com.prookie.kotlinmvp.R
import com.prookie.kotlinmvp.adapter.ArticleAdapter
import com.prookie.kotlinmvp.application.MainApplication
import com.prookie.kotlinmvp.base.BasePageResponse

import com.prookie.kotlinmvp.base.SwipeRefreshFragment
import com.prookie.kotlinmvp.bean.Article
import com.prookie.kotlinmvp.bean.Category
import com.prookie.kotlinmvp.bean.UserInfo
import com.prookie.kotlinmvp.biz.constract.IHomeView
import com.prookie.kotlinmvp.biz.presenter.HomePresenter
import com.prookie.kotlinmvp.utils.KCache
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


/**
 * HomeFragment
 * Created by brin on 2018/6/16.
 */
class HomeFragment : SwipeRefreshFragment(), IHomeView {


    companion object {
        private const val TAG: String = "HomeFragment"
    }

    private lateinit var recyclerView: RecyclerView
    private val mArticleList = mutableListOf<Article>()
    private val articleAdapter: ArticleAdapter by lazy { ArticleAdapter(activity, mArticleList) }
    private val mPresenter: HomePresenter by lazy { HomePresenter() }
    private var mCurPage: Int = 0

    /**
     * 懒加载：获取布局id
     */
    override fun getContentLayoutId(): Int = R.layout.fragment_home

    override fun initView(view: View) {

        //不管怎么说，感觉kotlin代码可读性较差
        // find view
        recyclerView = view.findViewById(R.id.rv_home) as RecyclerView
        swipeRefresh = view.findViewById(R.id.swipeRefresh_home) as SwipeRefreshLayout

        //init swipeRefreshLayout
        swipeRefresh.setOnRefreshListener(this)
        swipeRefresh.setColorSchemeColors(R.color.app_main_theme)

        // init  RecyclerView
        setupRecyclerView()

        mPresenter.attachView(this)
        super.initView(view)
    }

    private fun setupRecyclerView() {
        recyclerView.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = articleAdapter
        }
        //adapter regist listener
        articleAdapter.run {
            //下拉加载更多
            setOnLoadMoreListener(this@HomeFragment)
            //item点击事件
            setOnItemClickListener { _, _, position ->
                //跳转文章内容界面
                ArticleContentActivity.start(activity, mArticleList[position].link)

            }
            //item中的控件点击事件
            setOnItemChildClickListener { _, view, position ->
                when (view.id) {
                //文章分类
                    R.id.tv_article_classify -> {
                        val article = mArticleList[position]
                        val category = Category(article.chapterId, article.chapterName!!, 0, 0, 0, 0, listOf())
                        CategoryArticleActivity.start(activity, listOf(category))
                    }
                //收藏
                    R.id.iv_article_like -> {
                        val userInfo = MainApplication.instance().userInfo
                        if (userInfo == null || userInfo.id == 0 || userInfo.username.isNullOrBlank() || userInfo.password.isNullOrBlank())
                            activity.startActivity<LoginActivity>()//去登录
                        else
                            mPresenter.collect(mArticleList[position].id)//是否收藏
                    }
                }
            }
        }

    }


    /**
     * initData
     */
    override fun initData() {
        mPresenter.articles(mCurPage)
    }


    /**
     * 刷新
     */
    override fun onRefresh() {
        mCurPage = 0
        initData()
    }

    /**
     * 加载更多
     */
    override fun onLoadMoreRequested() {
        mCurPage++
        initData()
        super.onLoadMoreRequested()
    }


    /**
     * 获取文章列表成功
     */
    override fun articlesSuccess(data: BasePageResponse<Article>) {

        if (swipeRefresh.isRefreshing) {
            mArticleList.clear()
        }
        mArticleList.addAll(data.datas)
        articleAdapter.notifyDataSetChanged()

    }

    /**
     * 获取文章列表失败
     */
    override fun articlesFailure(errorMsg: String?) = activity.toast(errorMsg + "")

    /**
     * 收藏成功
     */
    override fun collectSuccess() {
        activity.toast("收藏成功")
    }

    /**
     * 收藏失败
     */
    override fun collectFailure(errorMsg: String?) = articlesFailure(errorMsg)


    override fun hideLoading() {
        super.hideLoading()
        if (swipeRefresh.isRefreshing) swipeRefresh.isRefreshing = false
        else articleAdapter.loadMoreComplete()
    }

}