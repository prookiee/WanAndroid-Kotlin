package com.prookie.kotlinmvp.biz.ui

import android.app.Activity
import android.util.Log
import android.view.Menu
import android.view.MenuItem

import android.widget.LinearLayout
import com.just.agentweb.AgentWeb
import com.just.agentweb.ChromeClientCallbackManager


import com.prookie.kotlinmvp.R
import com.prookie.kotlinmvp.base.BaseActivity
import kotlinx.android.synthetic.main.activity_article_content.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class ArticleContentActivity : BaseActivity() {

    companion object {
        const val TAG = "ArticleContentActivity"
        fun start(activity: Activity, link: String) =
                activity.startActivity<ArticleContentActivity>(Pair("link", link))
    }


    private lateinit var agentWeb: AgentWeb
    private lateinit var shareUrl: String

    override fun getContentLayoutId(): Int = R.layout.activity_article_content


    override fun initView() {

        val link = intent.extras.getString("link")

        toolbar_article_content.run {
            title = getString(R.string.loading)
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        agentWeb = AgentWeb.with(this@ArticleContentActivity)
                .setAgentWebParent(web_content_article, LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()// 使用默认进度条
                .defaultProgressBarColor()
                .setReceivedTitleCallback(receivedTitleCallback) //设置 Web 页面的 title 回调
                .createAgentWeb()//
                .ready()
                .go(link)


    }

    override fun initData() = Unit

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_article_content, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.menuShare -> {
                toast("wait...")
                return true
            }
            R.id.menuLike -> {
                toast("wait...")
                return true
            }
            R.id.menuBrowser -> {
                toast("wait...")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        agentWeb.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onPause() {
        agentWeb.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        agentWeb.webLifeCycle.onDestroy()
        super.onDestroy()
    }

    private val receivedTitleCallback =
            ChromeClientCallbackManager.ReceivedTitleCallback { _, title ->
                title?.let {
                    //                    toolbar.title = it
                }
            }


}
