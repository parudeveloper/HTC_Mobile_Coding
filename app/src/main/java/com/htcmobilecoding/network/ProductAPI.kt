package com.htcmobilecoding.network

import com.htcmobilecoding.model.ProductDetails
import com.htcmobilecoding.model.ProductItem
import com.htcmobilecoding.utils.END_URL
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductAPI {

    @GET(END_URL)
    suspend fun getAllProducts(): List<ProductItem>?

    @GET("product-details/{id}.json")
    suspend fun getProduct(@Path("id") productId: Int): ProductDetails


}