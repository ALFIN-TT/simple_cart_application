package com.alfin.productlistingapp.domain.usecase

import com.alfin.productlistingapp.data.network.response.Product
import kotlinx.coroutines.flow.Flow

interface GetCartProductUseCase {
    suspend operator fun invoke(id: Int): Flow<Product?>
}