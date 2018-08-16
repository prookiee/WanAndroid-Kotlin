package com.prookie.kotlinmvp.biz.ui

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.prookie.kotlinmvp.R
import com.prookie.kotlinmvp.adapter.ArticleAdapter
import com.prookie.kotlinmvp.base.BasePageResponse
import com.prookie.kotlinmvp.base.SwipeRefreshFragment
import com.prookie.kotlinmvp.bean.Article
import com.prookie.kotlinmvp.bean.Category
import com.prookie.kotlinmvp.biz.constract.IArticleView
import com.prookie.kotlinmvp.biz.presenter.ArticleListPresenter
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * ArticleListFragment
 * Created by brin on 2018-08-05.
 */
class ArticleListFragment : SwipeRefreshFragment(), IArticleView {

    companion object {
        private const val TAG = "ArticleListFragment"
        fun newInstance(category: Category): ArticleListFragment {
            val bundle = Bundle()
            bundle.putParcelable("category", category)
            val fragment = ArticleListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    //view
    private lateinit var recyclerView: RecyclerView
    //other
    private val mArticleList = mutableListOf<Article>()
    private var mCurPage: Int = 0
    private lateinit var mCategory: Category
    private val mAdapter: ArticleAdapter by lazy {
        ArticleAdapter(activity, mArticleList)
    }
    private val mListPresenter: ArticleListPresenter by lazy {
        ArticleListPresenter()
    }


    override fun getContentLayoutId(): Int = R.layout.fragment_article_list

    override fun initView(view: View) {

        //getData
        if (arguments.getParcelable<Category>("category") == null) {
            return
        }
        mCategory = arguments.getParcelable<Category>("category")
        // find view
        recyclerView = view.findViewById(R.id.rv_article) as RecyclerView
        swipeRefresh = view.findViewById(R.id.swipe_refresh_article) as SwipeRefreshLayout
        //init swipeRefreshLayout
        swipeRefresh.setOnRefreshListener(this)
        swipeRefresh.setColorSchemeColors(R.color.app_main_theme)
        // init  RecyclerView
        setupRecyclerView()

        mListPresenter.attachView(this)
        super.initView(view)

    }

    override fun initData() {
        mListPresenter.articles(mCurPage, mCategory.id)
    }

    override fun onRefresh() {
        mCurPage = 0
        initData()
    }


    private fun setupRecyclerView() {
        recyclerView.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapter
        }
        //adapter regist listener
        mAdapter.run {
            //item点击事件
            setOnItemClickListener { _, _, position ->
                //跳转文章内容界面
                ArticleContentActivity.start(activity, mArticleList[position].link)
            }
            //item中的控件点击事件
            setOnItemChildClickListener { _, view, position ->
                when (view.id) {
                    R.id.tv_article_classify -> activity.startActivity<LoginActivity>()//文章分类
//                    R.id.iv_article_like -> mListPresenter.collect(mArticleList[position].id)//是否收藏
                }
            }
            //上拉加载更多
            setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
                mCurPage++
                initData()
            })

            loadMoreEnd()
        }
    }


    override fun hideLoading() {
        super.hideLoading()
        if (swipeRefresh.isRefreshing) swipeRefresh.isRefreshing = false
        if (mAdapter.isLoading) mAdapter.loadMoreEnd()
    }


    override fun articlesSuccess(page: BasePageResponse<Article>) {
        if (swipeRefresh.isRefreshing) {
            mArticleList.clear()
        }
        mArticleList.addAll(page.datas)
        mAdapter.notifyDataSetChanged()
    }

    override fun articlesFailure(errorMsg: String) = activity.toast(errorMsg + "")
}