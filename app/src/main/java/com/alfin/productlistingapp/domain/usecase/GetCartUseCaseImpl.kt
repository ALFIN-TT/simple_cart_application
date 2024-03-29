package com.alfin.productlistingapp.domain.usecase

import com.alfin.productlistingapp.data.network.response.Product
import com.alfin.productlistingapp.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartUseCaseImpl @Inject constructor(
    private val repository: ProductRepository
) : GetCartUseCase {
    override suspend fun invoke(): Flow<List<Product>> = repository.getCart()
}