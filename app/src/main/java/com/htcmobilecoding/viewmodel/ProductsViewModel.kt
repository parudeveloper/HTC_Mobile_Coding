package com.htcmobilecoding.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.htcmobilecoding.data.Resource
import com.htcmobilecoding.model.ProductItem
import com.htcmobilecoding.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(private val productsRepository: ProductsRepository) :
    ViewModel() {

    private val _products = MutableStateFlow<Resource<List<ProductItem>>>(Resource.Loading())
    val products: StateFlow<Resource<List<ProductItem>>> = _products.asStateFlow()

    init {
        getAllProducts()
    }

    private fun getAllProducts() {
        viewModelScope.launch {
            productsRepository.getAllProducts().catch {
                _products.value = Resource.Error(it.message!!)
            }.collect {
                Log.d("ProductsViewModel", "getAllProducts: $it")
                _products.value = it
            }
        }
    }
}