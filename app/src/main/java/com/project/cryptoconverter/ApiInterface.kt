package com.project.cryptoconverter

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {
    @GET("markets?vs_currency=gbp&order=market_cap_desc&per_page=100&page=1&sparkline=false")
//    fun fetchAllCrypto(@Query("category_id") category_id: Int, @Query("page") page_id : Int): Call<List<Data>>
    fun fetchAllCrypto(): Call<List<Data>>

}