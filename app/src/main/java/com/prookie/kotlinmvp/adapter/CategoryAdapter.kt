package com.prookie.kotlinmvp.adapter

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.prookie.kotlinmvp.R
import com.prookie.kotlinmvp.bean.Category


/**
 * CategoryAdapter
 * Created by brin on 2018-08-03.
 */
class CategoryAdapter(val context: Context, datas: MutableList<Category>) :
        BaseQuickAdapter<Category, BaseViewHolder>(R.layout.rv_item_category, datas) {

    override fun convert(helper: BaseViewHolder, item: Category?) {
        item ?: return
        @Suppress("DEPRECATION")
        helper.setText(R.id.tv_main_title, item.name + "")
        item.children.let { children ->
            helper.setText(
                    R.id.tv_sub_title,
                    children.joinToString("     ", transform = { child ->
                        child.name.toString()
                    })
            )
        }

    }
}