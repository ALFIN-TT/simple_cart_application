package com.alfin.productlistingapp.domain.usecase

import androidx.paging.PagingData
import com.alfin.productlistingapp.data.network.response.Product
import kotlinx.coroutines.flow.Flow

interface GetProductsUseCase {
    suspend operator fun invoke(): Flow<PagingData<Product>>
}