package com.alfin.simplecartapp.domain.usecase

import com.alfin.simplecartapp.data.network.response.Product
import com.alfin.simplecartapp.domain.repository.ProductRepository
import javax.inject.Inject

class DeleteCartProductUseCaseImpl @Inject constructor(
    private val repository: ProductRepository
) : DeleteCartProductUseCase {
    override suspend fun invoke(product: Product) {
        repository.deleteCartProduct(product = product)
    }
}