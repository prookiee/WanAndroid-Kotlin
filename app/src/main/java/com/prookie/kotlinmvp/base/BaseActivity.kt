package com.prookie.kotlinmvp.base


import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import com.prookie.kotlinmvp.base.mvp.IBaseView
import com.prookie.kotlinmvp.utils.RxPermissionHelper
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import org.greenrobot.eventbus.EventBus


abstract class BaseActivity : RxAppCompatActivity(), IBaseView, RxPermissionHelper.PermissionCallbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentLayoutId())
        //注册eventBus
        if (userEventBus()) EventBus.getDefault().register(this)
        initView()
        initData()

    }


    override fun onDestroy() {
        super.onDestroy()
        //注销eventBus
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
    abstract fun initView()

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
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive && currentFocus != null && currentFocus.windowToken != null) {
            imm.hideSoftInputFromWindow(currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }


    /**
     * rxJava绑定生命周期
     */
    override fun <M> bindLifecycle(): LifecycleTransformer<M> = super.bindUntilEvent(ActivityEvent.PAUSE)

    override fun showLoading() = Unit

    override fun hideLoading() = Unit

}
