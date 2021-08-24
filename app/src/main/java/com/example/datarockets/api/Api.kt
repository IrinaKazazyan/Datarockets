package com.example.datarockets.api

import com.example.datarockets.model.BeersListItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET(value = "beers")
    suspend fun getBeerList(): Response<List<BeersListItem>>

    @GET(value = "beers")
    suspend fun getBeerListByPage(
        @Query("page") page: Int,
    ): Response<ArrayList<BeersListItem>>

    @GET(value = "beers/{id}")
    suspend fun getBeerItemById(@Path("id") id: Int?): Response<List<BeersListItem>>

    @GET(value = "beers/random")
    suspend fun getRandomBeerItem(): Response<List<BeersListItem>>
}