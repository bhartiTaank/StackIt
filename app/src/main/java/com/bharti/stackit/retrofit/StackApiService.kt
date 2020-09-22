package com.bharti.stackit.retrofit

import com.bharti.stackit.retrofit.model.StackApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface StackApiService {

    @GET("questions")
    suspend fun getStackQuestions(
        @Query("order") order: String? = null,
        @Query("sort") sort: String? = null,
        @Query("site") site: String,
        @Query("page") page: String? = null,
        @Query("pagesize") pageSize: Int? = null
    ): StackApiResponse
}