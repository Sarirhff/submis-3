package com.example.submission2.API

import com.example.submission2.model.DetailUserResponse
import com.example.submission2.model.User
import com.example.submission2.model.UserRespon
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET ("search/users" )
    @Headers ("Autorization: token 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")
    fun getSearchUser(
        @Query("q")query:String
    ): Call<UserRespon>

    @GET ("users/{username}")
    @Headers ("Autorization: token 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")
    fun getUserDetail(
        @Path("username")username:String
    ): Call<DetailUserResponse>

    @GET ("users/{username}/followers")
    @Headers ("Autorization: token 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")
    fun getUserFollowers(
        @Path("username")username:String
    ): Call<ArrayList<User>>

    @GET ("users/{username}/following")
    @Headers ("Autorization: token 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")
    fun getUserFollowing(
        @Path("username")username:String
    ): Call<ArrayList<User>>
}