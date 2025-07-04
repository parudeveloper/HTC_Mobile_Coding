package com.htcmobilecoding.viewmodel

import androidx.lifecycle.ViewModel
import com.htcmobilecoding.data.Resource
import androidx.lifecycle.viewModelScope
import com.htcmobilecoding.model.ProductDetails
import com.htcmobilecoding.repository.ProductDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ProductDetailsViewModel @Inject constructor(private val productDetailsRepository: ProductDetailsRepository) :
    ViewModel() {

    private val _productDetails = MutableStateFlow<Resource<ProductDetails>>(Resource.Loading())
    val productDetails: StateFlow<Resource<ProductDetails>> = _productDetails.asStateFlow()


    fun getProductDetails(productId: Int) {
        viewModelScope.launch {
            productDetailsRepository.getProductDetails(productId).catch {
                _productDetails.value = Resource.Error(it.message!!)
            }.collect {
                _productDetails.value = it

            }

        }
    }
}