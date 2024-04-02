package com.alfin.simplecartapp.domain.usecase

import com.alfin.simplecartapp.data.network.response.Product
import com.alfin.simplecartapp.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartProductUseCaseImpl @Inject constructor(
    private val repository: ProductRepository
) : GetCartProductUseCase {
    override suspend fun invoke(id: Int): Flow<Product> = repository.getCartProduct(id = id)
}