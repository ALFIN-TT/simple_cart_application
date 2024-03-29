package com.alfin.productlistingapp.domain.usecase

import com.alfin.productlistingapp.data.network.response.Product
import com.alfin.productlistingapp.domain.repository.ProductRepository
import javax.inject.Inject

class AddToCartUseCaseImpl @Inject constructor(
    private val repository: ProductRepository
) : AddToCartUseCase {
    override suspend fun invoke(product: Product): Long = repository.addToCart(product = product)
}