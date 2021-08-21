package com.example.datarockets.repository

import com.example.datarockets.api.Api
import com.example.datarockets.api.RetrofitInstance


class Repository {

    private val retrofitInstance = RetrofitInstance.getRetrofitInstance().create(Api::class.java)

    suspend fun getBeersList() = retrofitInstance.getBeerList()

    suspend fun getRefreshBeerList(page: Int, perPage: Int) =
        retrofitInstance.getRefreshBeerList(page, perPage)

    suspend fun getBeerItem(id: Int) = retrofitInstance.getBeerItem(id)
}