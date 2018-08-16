package com.prookie.kotlinmvp.application


import com.prookie.kotlinmvp.bean.UserInfo
import com.prookie.kotlinmvp.constant.SpKeyConst
import com.prookie.kotlinmvp.utils.KCache
import org.litepal.LitePalApplication
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * MainApplication
 * Created by brin on 2018/6/13.
 */
class MainApplication : LitePalApplication() {

    var userInfo: UserInfo? by KCache(this, SpKeyConst.USER_INFO, UserInfo(0, "", "", "", 0, null))

    override fun onCreate() {
        super.onCreate()
        instance = this

    }

    companion object {
        private var instance: MainApplication by NotNullSingleValueVar()
        fun instance(): MainApplication = instance


    }


    class NotNullSingleValueVar<T>() : ReadWriteProperty<Any?, T> {
        private var value: T? = null
        override fun getValue(thisRef: Any?, property: KProperty<*>): T =
                value ?: throw IllegalStateException("application is not init")


        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            this.value = if (this.value == null) value
            else throw IllegalStateException("application already init")
        }

    }

}