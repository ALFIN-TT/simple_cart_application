package com.alfin.productlistingapp.domain.usecase

import com.alfin.productlistingapp.data.network.response.Product
import com.alfin.productlistingapp.domain.repository.ProductRepository
import javax.inject.Inject

class DeleteCartProductUseCaseImpl @Inject constructor(
    private val repository: ProductRepository
) : DeleteCartProductUseCase {
    override suspend fun invoke(product: Product) {
        repository.deleteCartProduct(product = product)
    }
}