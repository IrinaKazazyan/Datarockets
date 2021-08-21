package com.example.datarockets.api

import com.example.datarockets.model.BeersList
import com.example.datarockets.model.BeersListItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET(value = "beers")
    suspend fun getBeerList(): Response<BeersList>

    @GET(value = "beers")
    suspend fun getRefreshBeerList(@Query("page") page: Int, @Query("per_page") perPage: Int ): Response<BeersList>

    @GET(value = "beers/{id}")
    suspend fun getBeerItem(@Path("id") id: Int?): Response<List<BeersListItem>>
}