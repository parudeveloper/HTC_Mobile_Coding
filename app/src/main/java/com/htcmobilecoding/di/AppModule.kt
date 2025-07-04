package com.htcmobilecoding.di

import com.htcmobilecoding.network.ProductAPI
import com.htcmobilecoding.repository.ProductsRepository
import com.htcmobilecoding.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofitInstance(): ProductAPI {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ProductAPI::class.java)
    }


    @Provides
    fun providesProductsRepository(productAPI: ProductAPI): ProductsRepository {
        return ProductsRepository(productAPI)
    }
}