package com.prookie.kotlinmvp.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

/**
 * UserInfo
 * Created by brin on 2018-08-09.
 */

data class UserInfo(var id: Int?,
                    var username: String?,
                    var password: String?,
                    var icon: String?,
                    var type: Int?,
                    var collectIds: List<Int>?) : Serializable {

//    companion object {
//        val instance: UserInfo by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { UserInfo(0, "", "", "", 0, null) }
//    }


}