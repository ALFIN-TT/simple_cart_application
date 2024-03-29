package com.alfin.simplecartapp.domain.usecase

import com.alfin.simplecartapp.data.network.response.Product
import com.alfin.simplecartapp.domain.repository.ProductRepository
import javax.inject.Inject

class AddToCartUseCaseImpl @Inject constructor(
    private val repository: ProductRepository
) : AddToCartUseCase {
    override suspend fun invoke(product: Product): Long = repository.addToCart(product = product)
}