package com.example.datarockets.repository

import com.example.datarockets.api.Api
import com.example.datarockets.api.RetrofitInstance


class Repository {

    private val retrofitInstance = RetrofitInstance.getRetrofitInstance().create(Api::class.java)

    suspend fun getBeerList() = retrofitInstance.getBeerList()

    suspend fun getBeerListByPage(page: Int) =
        retrofitInstance.getBeerListByPage(page)

    suspend fun getBeerItemById(id: Int) =
        retrofitInstance.getBeerItemById(id)
}