package com.example.demo.ApiService

import com.example.demo.Modal.DummyProducts
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/products")
    fun getProducts(): Call<DummyProducts>

}