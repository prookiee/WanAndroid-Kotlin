package com.prookie.kotlinmvp.biz.ui

import android.animation.Animator
import android.app.Activity
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter

import com.prookie.kotlinmvp.R
import com.prookie.kotlinmvp.adapter.ArticleAdapter
import com.prookie.kotlinmvp.application.MainApplication
import com.prookie.kotlinmvp.base.BasePageResponse
import com.prookie.kotlinmvp.base.SwipeRefreshActivity
import com.prookie.kotlinmvp.bean.Article
import com.prookie.kotlinmvp.bean.Category
import com.prookie.kotlinmvp.bean.SearchHistory
import com.prookie.kotlinmvp.biz.constract.IArticleView
import com.prookie.kotlinmvp.biz.constract.ISearchResultView
import com.prookie.kotlinmvp.biz.presenter.ArticleListPresenter
import com.prookie.kotlinmvp.biz.presenter.SearchResultPresenter
import com.prookie.kotlinmvp.listener.SimpleAnimationListener
import com.prookie.kotlinmvp.utils.LogUtil
import com.prookie.kotlinmvp.utils.SampleSuggestionBuilder
import kotlinx.android.synthetic.main.activity_search_result.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.cryse.widget.persistentsearch.PersistentSearchView
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.litepal.LitePal

/**
 * SearchResultActivity
 */
class SearchResultActivity : SwipeRefreshActivity(), ISearchResultView {

    companion object {
        private const val TAG = "SearchResultActivity"
        fun start(activity: Activity, key: String) = activity.startActivity<SearchResultActivity>(Pair("searchKey", key))
    }


    //view
    private lateinit var recyclerView: RecyclerView
    //other
    private val mArticleList = mutableListOf<Article>()
    private var mCurPage: Int = 0
    private val mAdapter: ArticleAdapter by lazy { ArticleAdapter(this, mArticleList) }
    private val mPresenter: SearchResultPresenter by lazy { SearchResultPresenter() }
    private lateinit var mSearchMenuItem: MenuItem

    private lateinit var mSearchKey: String

    override fun getContentLayoutId(): Int = R.layout.activity_search_result

    override fun initView() {
        //get intent data
        mSearchKey = intent.extras.getString("searchKey")
        // find view
        recyclerView = findViewById(R.id.rv_article_search_result) as RecyclerView
        swipeRefresh = findViewById(R.id.swipe_refresh_search_result) as SwipeRefreshLayout
        //init swipeRefreshLayout
        swipeRefresh.setOnRefreshListener(this)
        swipeRefresh.setColorSchemeColors(R.color.app_main_theme)

        //setupToolbar
        setupToolbar()
        //setupRecyclerView
        setupRecyclerView()
        //setupSearchView
        setupSearchView()

        mPresenter.attachView(this)
        super.initView()
    }

    /**
     * setupToolbar
     */
    private fun setupToolbar() {
        toolbar_search_result.run {
            title = mSearchKey
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    /**
     * setupSearchView
     */
    private fun setupSearchView() {

        search_tint_view_search.setOnClickListener {
            searchView_search.cancelEditing()
        }

        searchView_search.run {
            setSuggestionBuilder(SampleSuggestionBuilder())

            //搜索事件监听
            setSearchListener(object : PersistentSearchView.SearchListener {

                override fun onSearch(query: String?) {
                    if (!query.isNullOrBlank()) {
                        mCurPage = 0
                        mArticleList.clear()
                        mPresenter.searchList(mCurPage, query!!)
                        mSearchKey = query
                        val searchList: MutableList<SearchHistory> = LitePal.findAll(SearchHistory::class.java)
                        searchList
                                .asSequence()
                                .filter { it.value == query }
                                .forEach { return }
                        val history = SearchHistory(query, query)
                        history.save()
                    }
                }

                override fun onSearchExit() {
                    LogUtil.d(MainActivity.TAG, "onSearchExit")
                }

                override fun onSearchCleared() {
                    LogUtil.d(MainActivity.TAG, "onSearchCleared")
                }

                override fun onSearchTermChanged(term: String?) {
                    LogUtil.d(MainActivity.TAG, "onSearchTermChanged:$term")
                }

                override fun onSearchEditBackPressed(): Boolean {
                    if (isEditing) {
                        cancelEditing()
                        return true
                    }
                    return false
                }

                override fun onSearchEditClosed() = search_tint_view_search
                        .animate()
                        .alpha(0.0f)
                        .setDuration(300)
                        .setListener(object : SimpleAnimationListener() {
                            override fun onAnimationEnd(animation: Animator?) {
                                super.onAnimationEnd(animation)
                                search_tint_view_search.visibility = View.GONE
                            }
                        }).start()

                override fun onSearchEditOpened() = search_tint_view_search.run {
                    visibility = View.VISIBLE
                    animate().alpha(1.0f).setDuration(300).setListener(SimpleAnimationListener()).start()
                }
            })
        }
    }

    /**
     * setupRecyclerView
     */
    private fun setupRecyclerView() {
        recyclerView.run {
            layoutManager = LinearLayoutManager(this@SearchResultActivity)
            adapter = mAdapter
        }
        //adapter regist listener
        mAdapter.run {
            //item点击事件
            setOnItemClickListener { _, _, position ->
                //跳转文章内容界面
                ArticleContentActivity.start(this@SearchResultActivity, mArticleList[position].link)
            }
            //item中的控件点击事件
            setOnItemChildClickListener { _, view, position ->
                when (view.id) {
                    R.id.tv_article_classify -> {
                        val article = mArticleList[position]
                        val category = Category(article.chapterId, article.chapterName!!, 0, 0, 0, 0, listOf())
                        CategoryArticleActivity.start(this@SearchResultActivity, listOf(category))//文章分类
                    }
                    R.id.iv_article_like -> {
                        val userInfo = MainApplication.instance().userInfo
                        if (userInfo == null || userInfo.id == 0 || userInfo.username.isNullOrBlank() || userInfo.password.isNullOrBlank())
                            startActivity<LoginActivity>()//去登录
                        //else
//                            mPresenter.collect(mArticleList[position].id)//是否收藏
                    }
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

    /**
     * openSearch
     */
    private fun openSearch() {
        val menuItemView = findViewById(R.id.action_search)
        searchView_search.setStartPositionFromMenuItem(menuItemView)
        searchView_search.openSearch()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_searchview, menu)
        mSearchMenuItem = menu.findItem(R.id.action_search)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            when (item.itemId) {
                android.R.id.home -> {
                    finish()
                    return true
                }
                R.id.action_search -> {
                    openSearch()
                    return true
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun initData() {
        mPresenter.searchList(mCurPage, mSearchKey)
    }

    override fun onRefresh() {
        mCurPage = 0
        initData()
    }

    override fun hideLoading() {
        super.hideLoading()
        if (swipeRefresh.isRefreshing) swipeRefresh.isRefreshing = false
        searchView_search.closeSearch()

    }


    override fun searchSuccess(page: BasePageResponse<Article>) {
        if (swipeRefresh.isRefreshing) {
            mArticleList.clear()
        }
        mArticleList.addAll(page.datas)
        mAdapter.notifyDataSetChanged()
        toolbar_search_result.title = mSearchKey
    }

    override fun searchFailure(errorMsg: String) = toast(errorMsg + "")


}
