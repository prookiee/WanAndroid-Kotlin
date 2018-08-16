package com.prookie.kotlinmvp.base


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.prookie.kotlinmvp.base.mvp.IBaseView
import com.prookie.kotlinmvp.utils.RxPermissionHelper
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.components.support.RxFragment
import org.greenrobot.eventbus.EventBus

/**
 * BaseFragment
 * Created by brin on 2018/6/16.
 */
abstract class BaseFragment : RxFragment(), IBaseView, RxPermissionHelper.PermissionCallbacks {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(getContentLayoutId(), container, false)
        //注册eventBus
        if (userEventBus()) EventBus.getDefault().register(this)
        initView(view)
        initData()
        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        //注册eventBus
        if (userEventBus()) EventBus.getDefault().unregister(this)
        //关闭软键盘
        hideSoftKeyboard()
    }


    /**
     * 获取布局layoutId
     */
    abstract fun getContentLayoutId(): Int

    /**
     * initView
     */
    abstract fun initView(view: View)

    /**
     * initData
     */
    abstract fun initData()

    /**
     * 动态申请权限通过
     */
    override fun onPermissionsGranted() = Unit

    /**
     * 动态申请权限拒绝
     */
    override fun onPermissionsDenied() = Unit

    /**
     * 是否使用eventBus，默认不始用
     */
    open protected fun userEventBus(): Boolean = false

    /**
     * 关闭软键盘
     */
    open protected fun hideSoftKeyboard() {
        val imm: InputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive && activity.currentFocus != null && activity.currentFocus.windowToken != null) {
            imm.hideSoftInputFromWindow(activity.currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    /**
     * rxJava绑定生命周期
     */
    override fun <M> bindLifecycle(): LifecycleTransformer<M> = super.bindUntilEvent(FragmentEvent.STOP)

    override fun showLoading() = Unit

    override fun hideLoading() = Unit


}