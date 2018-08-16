package com.prookie.kotlinmvp.biz.ui


import android.app.Activity
import android.support.v4.app.Fragment
import com.prookie.kotlinmvp.R
import com.prookie.kotlinmvp.adapter.ViewPagerAdapter
import com.prookie.kotlinmvp.base.BaseActivity
import com.prookie.kotlinmvp.bean.Category
import kotlinx.android.synthetic.main.activity_category_article.*
import org.jetbrains.anko.startActivity


class CategoryArticleActivity : BaseActivity() {

    companion object {
        private const val TAG = "CategoryArticleActivity"

        fun start(activity: Activity, tabArray: List<Category>) = activity.startActivity<CategoryArticleActivity>(Pair("tabs_array", tabArray))

    }


    lateinit var tabsArray: List<Category>

    override fun getContentLayoutId(): Int = R.layout.activity_category_article

    override fun initView() {

        tabsArray = intent.extras.getParcelableArrayList("tabs_array")

        setupViewPager()
        setupTabLayout()

    }

    private fun setupTabLayout() {
        tab_category.setupWithViewPager(vp_category)
    }

    private fun setupViewPager() {
        val fragmentList: MutableList<Fragment> = mutableListOf()
        val adapter = ViewPagerAdapter(supportFragmentManager, fragmentList)

        for (item in tabsArray) {
            adapter.addFragment(ArticleListFragment.newInstance(item), item.name + "")
        }
        vp_category.adapter = adapter
    }

    override fun initData() = Unit


}
