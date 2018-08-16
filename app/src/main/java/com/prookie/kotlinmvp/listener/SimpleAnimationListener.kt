package com.prookie.kotlinmvp.listener

import android.animation.Animator

/**
 * SimpleAnimationListener
 * Created by brin on 2018-08-15.
 */
open class SimpleAnimationListener : Animator.AnimatorListener {

    override fun onAnimationRepeat(animation: Animator?) = Unit

    override fun onAnimationEnd(animation: Animator?) = Unit

    override fun onAnimationCancel(animation: Animator?) = Unit

    override fun onAnimationStart(animation: Animator?) = Unit

}