package com.htcmobilecoding.repository

import android.util.Log
import com.htcmobilecoding.data.Resource
import com.htcmobilecoding.model.ProductItem
import com.htcmobilecoding.network.ProductAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProductsRepository @Inject constructor(private val productAPI: ProductAPI) {

    fun getAllProducts(): Flow<Resource<List<ProductItem>>> = flow {
        emit(Resource.Loading())
        try {
            val products = productAPI.getAllProducts()
            Log.d("ProductsRepository", "getAllProducts: ${products?.size}")
            if (products != null) {
                emit(Resource.Success(products))
            } else {
                emit(Resource.Error("No products found."))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)


}

