package com.prookie.kotlinmvp.adapter

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.prookie.kotlinmvp.R

import com.prookie.kotlinmvp.bean.Article


/**
 * ArticleAdapter
 * Created by brin on 2018/7/4.
 */
class ArticleAdapter(val context: Context, datas: MutableList<Article>) :
        BaseQuickAdapter<Article, BaseViewHolder>(R.layout.rv_item_article, datas) {

    override fun convert(helper: BaseViewHolder, item: Article?) {
        item ?: return
        @Suppress("DEPRECATION")
        helper.setText(R.id.tv_article_title, item.title)//title
                .setText(R.id.tv_article_author, item.author)//作者
                .setText(R.id.tv_article_publish_time, item.niceDate)//发布时间
                .setText(R.id.tv_article_classify, item.chapterName)//分类名称
                .setImageResource(R.id.iv_article_like, if (item.collect) R.drawable.heart_like else R.drawable.heart_unlike)//是否收藏
                .addOnClickListener(R.id.tv_article_classify)
                .addOnClickListener(R.id.iv_article_like)
//                .linkify(R.id.tv_article_classify)
//        helper.setText(R.id.tv_article_title,item.title)
//                .setText(R.id.tv_article_author_name,item.author)
//                .setText(R.id.tv_article_time,item.publishTime)
//                .setI
    }
}