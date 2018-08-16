package com.prookie.kotlinmvp.utils

import android.util.Log

/**
 * LogUtil
 * Created by brin on 2018-08-15.
 */
object LogUtil {

    private const val VERBOSE = 1
    private const val DEBUG = 2
    private const val INFO = 3
    private const val WARN = 4
    private const val ERROR = 5
    private const val NOTHING = 6
    private var level = VERBOSE


    fun v(tag: String, msg: String) {
        if (level < VERBOSE) Log.v(tag, msg)
    }

    fun d(tag: String, msg: String) {
        if (level <= DEBUG) Log.d(tag, msg)
    }

    fun i(tag: String, msg: String) {
        if (level <= INFO) Log.i(tag, msg)

    }

    fun w(tag: String, msg: String) {
        if (level <= WARN) Log.w(tag, msg)
    }

    fun e(tag: String, msg: String) {
        if (level <= ERROR) Log.e(tag, msg)
    }

}