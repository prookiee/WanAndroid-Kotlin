package com.prookie.kotlinmvp.biz.ui


import com.prookie.kotlinmvp.R
import com.prookie.kotlinmvp.application.MainApplication
import com.prookie.kotlinmvp.base.BaseActivity
import com.prookie.kotlinmvp.bean.UserInfo
import com.prookie.kotlinmvp.biz.presenter.LoginPresenter
import com.prookie.kotlinmvp.biz.constract.ILoginView
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast


class LoginActivity : BaseActivity(), ILoginView {


    private val mPresenter: LoginPresenter by lazy { LoginPresenter() }


    override fun getContentLayoutId(): Int = R.layout.activity_login

    override fun initView() {

        mPresenter.attachView(this)
        //login
        btn_login.setOnClickListener {
            if (checkInput()) mPresenter.Login(et_username.text.toString(), et_password.text.toString())
        }
        //register
        btn_register.setOnClickListener {
            if (checkInput()) mPresenter.register(et_username.text.toString(), et_password.text.toString())
        }
    }

    override fun initData() = Unit


    override fun loginSuccess(userInfo: UserInfo) {
        MainApplication.instance().userInfo = userInfo
        finish()
        //目前不需要setResult
//        if (MainApplication.instance().userInfo != null) {
//            val intent = Intent()
//            setResult(Activity.RESULT_OK, intent)
//            finish()
//        }
    }

    override fun loginFailure(errorMsg: String?) = toast(errorMsg + "")

    override fun registerSuccess(userInfo: UserInfo) = loginSuccess(userInfo)

    override fun registerFailure(errorMsg: String?) = toast(errorMsg + "")

    /**
     * checkInput
     */
    private fun checkInput(): Boolean {
        //check username
        if (et_username.text.isNullOrBlank()) {
            et_username.error = getString(R.string.username_not_empty)
            et_username.requestFocus()
            return false
        }
        //check password
        if (et_password.text.isNullOrBlank()) {
            et_password.error = getString(R.string.password_not_empty)
            et_password.requestFocus()
            return false
        }
        if (et_password.text.length < 6) {
            et_password.error = getString(R.string.password_length_not_enough)
            et_password.requestFocus()
            return false
        }
        return true
    }


}
