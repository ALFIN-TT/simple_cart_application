package com.alfin.simplecartapp.domain.usecase

import androidx.paging.PagingData
import com.alfin.simplecartapp.data.network.response.Product
import kotlinx.coroutines.flow.Flow

interface GetProductsUseCase {
    suspend operator fun invoke(): Flow<PagingData<Product>>
}