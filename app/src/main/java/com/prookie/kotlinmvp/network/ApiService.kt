package com.prookie.kotlinmvp.network

import com.prookie.kotlinmvp.base.BasePageResponse
import com.prookie.kotlinmvp.base.BaseResponse
import com.prookie.kotlinmvp.bean.Article
import com.prookie.kotlinmvp.bean.Category
import com.prookie.kotlinmvp.bean.HotKey
import com.prookie.kotlinmvp.bean.UserInfo
import io.reactivex.Observable
import retrofit2.http.*

/**
 * ApiService
 * Created by brin on 2018/6/13.
 */
interface ApiService {

    /**
     * 首页数据
     * http://www.wanandroid.com/article/list/0/json
     * @param page page
     */
    @GET("/article/list/{page}/json")
    fun articles(@Path("page") page: Int): Observable<BaseResponse<BasePageResponse<Article>>>


    /**
     * 知识体系
     * http://www.wanandroid.com/tree/json
     */
    @GET("/tree/json")
    fun categoryTreeList(): Observable<BaseResponse<List<Category>>>

    /**
     * 知识体系下的文章
     * http://www.wanandroid.com/article/list/0/json?cid=168
     * @param page page
     * @param cid cid
     */
    @GET("/article/list/{page}/json")
    fun articles(@Path("page") page: Int, @Query("cid") cid: Int): Observable<BaseResponse<BasePageResponse<Article>>>


    /**
     * 收藏文章
     * @param id id
     * @return Deferred<HomeListResponse>
     */
    @POST("/lg/collect/{id}/json")
    fun collectArticle(@Path("id") id: Int): Observable<BaseResponse<Any>>


    /**
     * 大家都在搜
     * http://www.wanandroid.com/hotkey/json
     */
    @GET("/hotkey/json")
    fun hotKeys(): Observable<BaseResponse<List<HotKey>>>


    /**
     * 常用网站
     * http://www.wanandroid.com/friend/json
     */
    @GET("/friend/json")
    fun hotWebsite(): Observable<BaseResponse<List<HotKey>>>//为了方便，字段共用hotkey


    /**
     * 搜索
     * http://www.wanandroid.com/article/query/0/json
     * @param page page
     * @param k POST search key
     */
    @POST("/article/query/{page}/json")
    @FormUrlEncoded
    fun searchList(@Path("page") page: Int, @Field("k") k: String): Observable<BaseResponse<BasePageResponse<Article>>>


    /**
     * 登录
     * @param username username
     * @param password password
     * @return Deferred<LoginResponse>
     */
    @POST("/user/login")
    @FormUrlEncoded
    fun login(
            @Field("username") username: String,
            @Field("password") password: String
    ): Observable<BaseResponse<UserInfo>>

    /**
     * 注册
     * @param username username
     * @param password password
     * @param repassword repassword
     * @return Deferred<LoginResponse>
     */
    @POST("/user/register")
    @FormUrlEncoded
    fun register(
            @Field("username") username: String,
            @Field("password") password: String,
            @Field("repassword") repassowrd: String
    ): Observable<BaseResponse<UserInfo>>


}