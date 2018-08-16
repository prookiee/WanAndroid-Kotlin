package com.prookie.kotlinmvp.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * 体系分类
 * Created by brin on 2018-08-03.
 */
@Parcelize
data class Category(var id:Int,var name:String,var courseId:Int,var parentChapterId : Int,var order :Int,var visible:Int,var children:List<Category>) : Parcelable {

//    var id: Int? = 0
//    var name: String? = null
//    var courseId: Int? = 0//课程id
//    var parentChapterId: Int? = 0//父id
//    var order: Int? = 0
//    var visible: Int? = 0
//    var children: List<Category>? = null//包含二级目录，甚至三级目录

    override fun toString(): String {
        return "Category(id=$id, name=$name, courseId=$courseId, parentChapterId=$parentChapterId, order=$order, visible=$visible, children=$children)"
    }


}