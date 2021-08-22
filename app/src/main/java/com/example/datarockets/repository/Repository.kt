package com.example.datarockets.repository

import com.example.datarockets.api.Api
import com.example.datarockets.api.RetrofitInstance


class Repository {

    private val retrofitInstance = RetrofitInstance.getRetrofitInstance().create(Api::class.java)

    suspend fun getBeerList(page: Int, perPage: Int) =
        retrofitInstance.getBeerList(page, perPage)

    suspend fun getBeerItem(id: Int) = retrofitInstance.getBeerItem(id)

}