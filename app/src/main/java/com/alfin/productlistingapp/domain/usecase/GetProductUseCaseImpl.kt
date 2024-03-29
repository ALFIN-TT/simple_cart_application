package com.alfin.productlistingapp.domain.usecase

import com.alfin.productlistingapp.core.data.network.Resource
import com.alfin.productlistingapp.domain.repository.ProductRepository
import com.alfin.productlistingapp.presentation.screen.productdetail.state.GetProductResponseData
import kotlinx.coroutines.flow.Flow

class GetProductUseCaseImpl(
    private val repository: ProductRepository
) : GetProductUseCase {
    override suspend fun invoke(productId: Int): Flow<Resource<GetProductResponseData>> =
        repository.getProduct(productId = productId)
}