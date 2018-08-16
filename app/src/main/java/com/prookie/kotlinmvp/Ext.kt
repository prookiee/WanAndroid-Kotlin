package com.prookie.kotlinmvp

/**
 * Created by brin on 2018-08-04.
 */
/**
 * get random color
 * @return 16777215 is FFFFFF, 0 is 000000
 */
fun getRandomColor(): String = "#${Integer.toHexString((Math.random() * 16777215).toInt())}"