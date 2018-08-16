package com.prookie.kotlinmvp.biz.ui


import android.animation.Animator
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import com.prookie.kotlinmvp.R
import com.prookie.kotlinmvp.adapter.ViewPagerAdapter
import com.prookie.kotlinmvp.base.BaseActivity
import com.prookie.kotlinmvp.bean.SearchHistory
import com.prookie.kotlinmvp.listener.SimpleAnimationListener
import com.prookie.kotlinmvp.utils.LogUtil
import com.prookie.kotlinmvp.utils.SampleSuggestionBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.cryse.widget.persistentsearch.PersistentSearchView
import org.litepal.LitePal


class MainActivity : BaseActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    override fun getContentLayoutId(): Int = R.layout.activity_main

    override fun initView() {

        setupViewPager()
        setupTabLayout()
        setupSearchView()

    }

    override fun initData() = Unit


    /**
     * setupSearchView
     */
    private fun setupSearchView() {

        search_tint_view.setOnClickListener { searchView.cancelEditing() }

        searchView.run {

            //home button
            setHomeButtonListener {
                drawer_layout.openDrawer(Gravity.START)
            }

            //搜索历史
            setSuggestionBuilder(SampleSuggestionBuilder())

            //搜索事件监听
            setSearchListener(object : PersistentSearchView.SearchListener {

                override fun onSearch(query: String?) {
                    LogUtil.d(TAG, "onSearch:$query")
                    if (!query.isNullOrBlank()) {
                        SearchResultActivity.start(this@MainActivity, query!!)
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
                    LogUtil.d(TAG, "onSearchExit")
                }

                override fun onSearchCleared() {
                    LogUtil.d(TAG, "onSearchCleared")
                }

                override fun onSearchTermChanged(term: String?) {
                    LogUtil.d(TAG, "onSearchTermChanged:$term")
                }

                override fun onSearchEditBackPressed(): Boolean {
                    LogUtil.d(TAG, "onSearchEditBackPressed")
                    return false
                }

                override fun onSearchEditClosed() = search_tint_view
                        .animate()
                        .alpha(0.0f)
                        .setDuration(300)
                        .setListener(object : SimpleAnimationListener() {
                            override fun onAnimationEnd(animation: Animator?) {
                                super.onAnimationEnd(animation)
                                search_tint_view.visibility = View.GONE
                            }
                        }).start()

                override fun onSearchEditOpened() = search_tint_view.run {
                    visibility = View.VISIBLE
                    animate().alpha(1.0f).setDuration(300).setListener(SimpleAnimationListener()).start()
                }
            })
        }
    }


    /**
     * setupViewPager
     */
    private fun setupViewPager() {
        val fragmentList = mutableListOf<Fragment>()
        val adapter = ViewPagerAdapter(supportFragmentManager, fragmentList)
        adapter.addFragment(HomeFragment(), "首页")
        adapter.addFragment(CategoryFragment(), "体系")
        adapter.addFragment(HotFragment(), "Hot")
        vp_main.adapter = adapter
    }

    /**
     * setupTabLayout
     */
    private fun setupTabLayout() = tab_main.run {
        setupWithViewPager(vp_main)
//            addOnTabSelectedListener(TabLayout.OnTabSelectedListener = )
    }


    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
            searchView.run {
                if (isEditing) {
                    cancelEditing()
                }
            }
        }
        return super.dispatchKeyEvent(event)
    }

    override fun onBackPressed() {
        drawer_layout.run {
            if (isDrawerOpen(GravityCompat.START)) {
                closeDrawer(GravityCompat.START)
                return
            }
        }
    }


}
