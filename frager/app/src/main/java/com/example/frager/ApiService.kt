package com.example.frager

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("users/")
    fun getAllUser(): Call<List<User>>

    @GET("users/{id}")
    fun getUserById(@Path("id") id: String): Call<User>

    @POST("users/")
    fun postUser(@Body user: User?): Call<User>
}