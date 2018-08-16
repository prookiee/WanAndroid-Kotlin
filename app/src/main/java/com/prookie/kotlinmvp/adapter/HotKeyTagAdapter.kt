package com.prookie.kotlinmvp.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.prookie.kotlinmvp.R
import com.prookie.kotlinmvp.bean.HotKey
import com.prookie.kotlinmvp.getRandomColor
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter

/**
 * HotKeyTagAdapter
 * Created by brin on 2018-08-04.
 */
class HotKeyTagAdapter(private val context: Context, private val datas: List<HotKey>) : TagAdapter<HotKey>(datas) {

    override fun getView(parent: FlowLayout?, position: Int, t: HotKey?): View =
            (LayoutInflater.from(context).inflate(R.layout.tag_item_hot_key, parent, false) as TextView).apply {
                text = datas[position].name
                val randomColor = try {
                    Color.parseColor(getRandomColor())
                } catch (e: Exception) {
                    @Suppress("DEPRECATION")
                    context.resources.getColor(R.color.colorAccent)
                }
                setTextColor(randomColor)
            }

}