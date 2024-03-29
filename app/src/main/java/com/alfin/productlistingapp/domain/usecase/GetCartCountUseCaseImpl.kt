package com.alfin.productlistingapp.domain.usecase

import com.alfin.productlistingapp.domain.repository.ProductRepository
import javax.inject.Inject

class GetCartCountUseCaseImpl @Inject constructor(
    private val repository: ProductRepository
) : GetCartCountUseCase {
    override suspend fun invoke(): Int = repository.getCartCount()
}