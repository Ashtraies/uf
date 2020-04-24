package org.ash.test.uf.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface GetUserService {

    @GET("api")
    fun getUsers(
        @Query("results") resultSize: Int
    ): Call<ResponseBody>
}