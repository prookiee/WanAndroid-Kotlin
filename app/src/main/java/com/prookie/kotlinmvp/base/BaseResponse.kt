package com.prookie.kotlinmvp.base

/**
 *
 * BaseResponse
 * Created by brin on 2018/6/13.
 *
 * @property errorCode     返回码
 * @property errorMsg  返回信息
 * @property data  返回内容
 *
 */
class BaseResponse<out T>(val errorCode: Int, val errorMsg: String, val data: T?) {


    override fun toString(): String
            = "BaseResponse(Code='$errorCode', Message='$errorMsg', Content=$data)"

}