package com.alfin.simplecartapp.domain.usecase

import com.alfin.simplecartapp.data.network.response.Product
import kotlinx.coroutines.flow.Flow

interface GetCartUseCase {
    suspend operator fun invoke():Flow<List<Product>>
}