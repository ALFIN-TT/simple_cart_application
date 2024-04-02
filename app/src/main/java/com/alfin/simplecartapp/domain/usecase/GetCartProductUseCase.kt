package com.alfin.simplecartapp.domain.usecase

import com.alfin.simplecartapp.data.network.response.Product
import kotlinx.coroutines.flow.Flow

interface GetCartProductUseCase {
    suspend operator fun invoke(id: Int): Flow<Product?>
}