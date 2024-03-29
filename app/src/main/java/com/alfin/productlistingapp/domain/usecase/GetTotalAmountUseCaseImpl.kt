package com.alfin.productlistingapp.domain.usecase

import com.alfin.productlistingapp.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTotalAmountUseCaseImpl @Inject constructor(
    private val repository: ProductRepository
) : GetTotalAmountUseCase {
    override suspend fun invoke(): Flow<Int> = repository.getTotalAmount()
}