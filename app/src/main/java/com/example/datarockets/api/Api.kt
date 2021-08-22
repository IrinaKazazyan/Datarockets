package com.example.datarockets.api

import com.example.datarockets.model.BeersListItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET(value = "beers")
    suspend fun getBeerList(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<ArrayList<BeersListItem>>

    @GET(value = "beers/{id}")
    suspend fun getBeerItem(@Path("id") id: Int?): Response<List<BeersListItem>>
}