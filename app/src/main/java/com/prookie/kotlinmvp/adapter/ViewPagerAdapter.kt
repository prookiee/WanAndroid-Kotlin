package com.prookie.kotlinmvp.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * ViewPagerAdapter
 * Created by brin on 2018-08-02.
 */
class ViewPagerAdapter(fm: FragmentManager, private val fragmentList: MutableList<Fragment>) : FragmentPagerAdapter(fm) {

    private val tabTitleList: MutableList<String> by lazy { mutableListOf<String>() }//使用的时候再去初始化

    override fun getItem(position: Int): Fragment = fragmentList[position]

    override fun getCount(): Int = fragmentList.size

    override fun getPageTitle(position: Int): CharSequence = tabTitleList[position]

    /**
     * for setup with tablayout
     */
    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        tabTitleList.add(title)
    }

    fun addFragment(fragment: Fragment) {
        fragmentList.add(fragment)
    }
}