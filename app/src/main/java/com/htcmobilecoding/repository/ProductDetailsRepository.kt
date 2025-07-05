package com.htcmobilecoding.repository

import com.htcmobilecoding.data.Resource
import com.htcmobilecoding.model.ProductDetails
import com.htcmobilecoding.network.ProductAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProductDetailsRepository @Inject constructor(private val productAPI: ProductAPI) {

    fun getProductDetails(productId: Int): Flow<Resource<ProductDetails>> = flow {
        emit(Resource.Loading())
        try {
            val response = productAPI.getProduct(productId)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown Error"))

        }

    }.flowOn(Dispatchers.IO)

}