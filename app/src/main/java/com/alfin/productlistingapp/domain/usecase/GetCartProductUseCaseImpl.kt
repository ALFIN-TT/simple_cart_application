package com.alfin.productlistingapp.domain.usecase

import com.alfin.productlistingapp.data.network.response.Product
import com.alfin.productlistingapp.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartProductUseCaseImpl @Inject constructor(
    private val repository: ProductRepository
) : GetCartProductUseCase {
    override suspend fun invoke(id: Int): Flow<Product> = repository.getCartProduct(id = id)
}