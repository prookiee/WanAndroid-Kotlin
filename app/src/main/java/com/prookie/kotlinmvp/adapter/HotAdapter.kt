package com.prookie.kotlinmvp.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.prookie.kotlinmvp.R
import com.prookie.kotlinmvp.bean.Hot
import com.prookie.kotlinmvp.biz.ui.ArticleContentActivity
import com.prookie.kotlinmvp.biz.ui.SearchResultActivity
import com.zhy.view.flowlayout.TagFlowLayout

/**
 * HotAdapter
 * Created by brin on 2018-08-04.
 */
class HotAdapter(private val context: Context, private val datas: List<Hot>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int = datas.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rv_item_hot, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {

        val vh: ItemViewHolder = holder as ItemViewHolder
        vh.tv_label.text = datas[position].label
        vh.tagFlowLayout.adapter = HotKeyTagAdapter(context, datas[position].hotKeyList)
        vh.tagFlowLayout.setOnTagClickListener { _, pos, _ ->
            when (datas[position].label) {
                context.getString(R.string.hot_search) -> {//热门搜索
                    val hotKey = datas[position].hotKeyList[pos];
                    SearchResultActivity.start(context as Activity, hotKey.name)
                }
                context.getString(R.string.hot_website) -> {//热门网站
                    ArticleContentActivity.start(context as Activity, datas[position].hotKeyList[pos].link.toString())
                }
            }
            true
        }
    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tv_label = view.findViewById(R.id.tv_label_hot) as TextView
        var tagFlowLayout = view.findViewById(R.id.tag_flow_hot_key) as TagFlowLayout
    }
}