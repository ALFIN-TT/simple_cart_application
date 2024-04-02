package com.alfin.simplecartapp.domain.usecase

import com.alfin.simplecartapp.core.data.network.Resource
import com.alfin.simplecartapp.domain.repository.ProductRepository
import com.alfin.simplecartapp.presentation.screen.productdetail.state.GetProductResponseData
import kotlinx.coroutines.flow.Flow

class GetProductUseCaseImpl(
    private val repository: ProductRepository
) : GetProductUseCase {
    override suspend fun invoke(productId: Int): Flow<Resource<GetProductResponseData>> =
        repository.getProduct(productId = productId)
}