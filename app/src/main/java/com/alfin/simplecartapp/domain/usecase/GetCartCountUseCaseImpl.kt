package com.alfin.simplecartapp.domain.usecase

import com.alfin.simplecartapp.domain.repository.ProductRepository
import javax.inject.Inject

class GetCartCountUseCaseImpl @Inject constructor(
    private val repository: ProductRepository
) : GetCartCountUseCase {
    override suspend fun invoke(): Int = repository.getCartCount()
}