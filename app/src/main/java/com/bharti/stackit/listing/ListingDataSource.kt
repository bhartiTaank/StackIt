package com.bharti.stackit.listing

import com.bharti.stackit.retrofit.RetrofitProvider
import com.bharti.stackit.retrofit.StackApiService
import com.bharti.stackit.retrofit.model.StackApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ListingDataSource {
    suspend fun fetchStackDataFromNetwork(): StackApiResponse {
        return withContext(Dispatchers.IO) {
            apiService.getStackQuestions(
                order = "desc",
                sort = "activity",
                site = "stackoverflow",
                pageSize = 100
            )
        }
    }

    private val apiService: StackApiService = RetrofitProvider.provideStackApiService()
}