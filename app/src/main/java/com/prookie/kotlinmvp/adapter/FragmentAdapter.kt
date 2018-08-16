package com.prookie.kotlinmvp.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.prookie.kotlinmvp.base.BaseFragment

/**
 * Created by brin on 2018/6/16.
 */
class FragmentAdapter(fm: FragmentManager, private val list: List<BaseFragment>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = list[position]

    override fun getCount(): Int = list.size
}