package com.prookie.kotlinmvp.behavior

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.View

/**
 * HeaderBehavior
 * Created by brin on 2018-08-14.
 */
class HeaderBehavior(context: Context, attrs: AttributeSet) : CoordinatorLayout.Behavior<View>(context, attrs) {

    override fun layoutDependsOn(parent: CoordinatorLayout?, child: View?, dependency: View?): Boolean = dependency is AppBarLayout

    override fun onDependentViewChanged(parent: CoordinatorLayout?, child: View?, dependency: View?): Boolean {
        //根据dependency top 值的变化改变 child 的 translationY
        val translationY = Math.abs(dependency!!.top).toFloat()
        child!!.translationY = -translationY
        return true
    }

}