package com.example.demo.ApiService

import com.example.demo.Modal.DummyProducts
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ClientApi {

    private const val BASE_URL = "https://dummyjson.com/"
    private val okHttpClient = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiService::class.java)

    fun getProducts(onSuccess: (product: DummyProducts) -> Unit, onError: (error: String) -> Unit) {
        try {
            val call = apiService.getProducts()
            call.enqueue(object : Callback<DummyProducts>{
                override fun onResponse(
                    call: Call<DummyProducts>?,
                    response: Response<DummyProducts>?
                ) {
                    if (response != null) {
                        if (response.isSuccessful) {
                            val product = response.body()
                            val limit: Int = product.limit
                            onSuccess(product)
                        } else {
                            onError("Api called failed with code ${response.code()}")
                        }
                    }
                }

                override fun onFailure(call: Call<DummyProducts>?, t: Throwable?) {
                    onError(t?.message ?: "UnknownError")
                }
            })

        }
        catch (e:java.net.SocketTimeoutException){
            onError("host not found")
        }

    }


}