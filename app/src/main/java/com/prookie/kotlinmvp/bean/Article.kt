package com.prookie.kotlinmvp.bean

/**
 * Created by brin on 2018/7/4.
 */
class Article(
        var id: Int,
        var originId: Int,
        var title: String,
        var chapterId: Int,
        var chapterName: String?,
        var envelopePic: Any,
        var link: String,
        var author: String,
        var origin: Any,
        var publishTime: Long,
        var zan: Any,
        var desc: Any,
        var visible: Int,
        var niceDate: String,
        var courseId: Int,
        var collect: Boolean
)