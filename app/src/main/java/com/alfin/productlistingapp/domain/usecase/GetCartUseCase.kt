package com.alfin.productlistingapp.domain.usecase

import com.alfin.productlistingapp.data.network.response.Product
import kotlinx.coroutines.flow.Flow

interface GetCartUseCase {
    suspend operator fun invoke():Flow<List<Product>>
}